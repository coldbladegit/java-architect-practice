package com.cold.blade.redis.service.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cold.blade.redis.service.RedisCacheService;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @version 1.0
 */
@Service
@Slf4j
public class RedisCacheServiceImpl implements RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> void set(String key, T value, long expire, TimeUnit timeUnit) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(expire >= 0);

        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public <T> Boolean setIfAbsent(String key, T value, long expire, TimeUnit timeUnit) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(expire >= 0);

        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, timeUnit);
    }

    @Override
    public Optional<String> getString(String key) {
        return get(key, String.class);
    }

    @Override
    public Optional<Byte> getByte(String key) {
        return get(key, Byte.class);
    }

    @Override
    public Optional<Integer> getInt(String key) {
        return get(key, Integer.class);
    }

    @Override
    public Optional<Long> getLong(String key) {
        return get(key, Long.class);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        Preconditions.checkNotNull(key);

        Object value = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return Optional.empty();
        }
        String jsonStr = JSON.toJSONString(value);
        return Optional.of(JSON.parseObject(jsonStr, clazz));
    }
}
