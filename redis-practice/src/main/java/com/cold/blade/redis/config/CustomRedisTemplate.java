package com.cold.blade.redis.config;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author cold_blade
 * @date 2020/1/3
 * @version 1.0
 */
@Component
public class CustomRedisTemplate<T> extends RedisTemplate<String, T> {

    private JedisConnectionFactory connectionFactory;
}
