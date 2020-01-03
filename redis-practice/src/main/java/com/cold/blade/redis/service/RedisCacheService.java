package com.cold.blade.redis.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @description Redis缓存功能相关接口
 * @author cold_blade
 * @version 1.0
 */
public interface RedisCacheService {

    /**
     * 缓存key对应的value(采用覆盖原则)
     *
     * @param key      键
     * @param value    值
     * @param expire   过期时间，必须大于0
     * @param timeUnit 时间单位
     */
    <T> void set(String key, T value, long expire, TimeUnit timeUnit);

    /**
     * 自增key对应的value
     *
     * @param key      键
     */
    void increment(String key);

    /**
     * 缓存key对应的value(如果存在则不缓存)
     *
     * @param key      键
     * @param value    值
     * @param expire   过期时间，必须大于0
     * @param timeUnit 时间单位
     * @return 成功缓存返回 {@code true},否则返回 {@code false}
     */
    <T> Boolean setIfAbsent(String key, T value, long expire, TimeUnit timeUnit);

    /**
     * 获取key对应的value
     *
     * @param key    键
     * @return {@code value} or {@code null}
     */
    Optional<String> getString(String key);

    /**
     * 获取key对应的value
     *
     * @param key    键
     * @return {@code value} or {@code null}
     */
    Optional<Byte> getByte(String key);

    /**
     * 获取key对应的value
     *
     * @param key    键
     * @return {@code value} or {@code null}
     */
    Optional<Integer> getInt(String key);

    /**
     * 获取key对应的value
     *
     * @param key    键
     * @return {@code value} or {@code null}
     */
    Optional<Long> getLong(String key);

    /**
     * 获取key对应的value
     *
     * @param key    键
     * @return {@code value} or {@code null}
     */
    <T> Optional<T> get(String key, Class<T> clazz);
}
