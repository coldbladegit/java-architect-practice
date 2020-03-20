package com.cold.blade.redis.service.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cold.blade.redis.service.RedisLockService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author cold_blade
 * @version 1.0
 */
@Slf4j
@Service
public class RedisLockServiceImpl implements RedisLockService {

    /**
     * 锁成功写入时的标记
     */
    private static final String LOCK_SUCCESS = "OK";
    /**
     * 不存在时写入锁的命令
     */
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * EX|PX, 锁过期时间单位: EX = seconds; PX = milliseconds
     */
    private static final String LOCK_EXPIRE_TIME_UNIT = "PX";
    /**
     * 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒
     */
    private static final long MILLI_2_NANO = 1000 * 1000L;
    /**
     * 默认锁的超时时间（豪秒），过期删除
     */
    private static final int DEF_LOCK_EXPIRE_MILLI = 5 * 60 * 1000;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Optional<String> lock(String key) {
        return lock(key, DEF_LOCK_EXPIRE_MILLI);
    }

    @Override
    public Optional<String> lock(String key, long lockExpireMillis) {
        final String requestId = generateRequestId();

        try {
            Boolean isLocked = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                String result = ((Jedis) redisConnection.getNativeConnection())
                    .set(key, requestId, SET_IF_NOT_EXIST, LOCK_EXPIRE_TIME_UNIT, lockExpireMillis);
                return LOCK_SUCCESS.equals(result);
            });
            return isLocked ? Optional.of(requestId) : Optional.empty();
        } catch (Exception e) {
            log.warn("====== failed to lock.", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> tryLock(String key, long tryExpireMillis) {
        return tryLock(key, tryExpireMillis, DEF_LOCK_EXPIRE_MILLI);
    }

    @Override
    public Optional<String> tryLock(String key, long tryExpireMillis, long lockExpireMillis) {
        // 超时判断的精度为纳秒级
        long nano = System.nanoTime();
        tryExpireMillis *= MILLI_2_NANO;
        final String requestId = generateRequestId();
        try {
            while ((System.nanoTime() - nano) < tryExpireMillis) {
                Boolean isLocked = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                    String result = ((Jedis) redisConnection.getNativeConnection())
                        .set(key, requestId, SET_IF_NOT_EXIST, LOCK_EXPIRE_TIME_UNIT, lockExpireMillis);
                    return LOCK_SUCCESS.equals(result);
                });
                if (isLocked) {
                    return Optional.of(requestId);
                }
                // 短暂休眠，避免出现活锁
                Thread.sleep(3);
            }
        } catch (Exception e) {
            log.warn("====== failed to lock.", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean unLock(String key, String requestId) {
        String command = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        try {
            return (boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                Long result = (Long) ((Jedis) redisConnection.getNativeConnection())
                    .eval(command, Collections.singletonList(key), Collections.singletonList(requestId));
                return result > 0;
            });
        } catch (Exception e) {
            log.warn("====== failed to unlock.", e);
        }

        return false;
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
