package com.cold.blade.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @description Redis连接的相关配置
 * @author cold_blade
 * @version 1.0
 */
@Configuration
@PropertySource(value = "classpath:spring-redis.properties")
public class RedisConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.db:0}")
    private int db;

    @Value("${spring.redis.max-idle:8}")
    private int maxIdle;
    @Value("${spring.redis.min-idle:0}")
    private int minIdle;
    @Value("${spring.redis.max-total:8}")
    private int maxTotal;
    @Value("${spring.redis.max-wait:30000}")
    private long maxWait;
    @Value("${spring.redis.timeout:5000}")
    private long timeout;

    @Bean
    public RedisStandaloneConfiguration standaloneConfiguration() {
        RedisStandaloneConfiguration standaloneCfg = new RedisStandaloneConfiguration(host, port);
        standaloneCfg.setDatabase(db);
        return standaloneCfg;
    }

    @Bean
    public JedisClientConfiguration clientConfiguration() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWait);

        return JedisClientConfiguration.builder()
            .connectTimeout(Duration.ofMillis(timeout))
            .usePooling()
            .poolConfig(poolConfig)
            .build();
    }

    @Bean
    public JedisConnectionFactory connectionFactory(RedisStandaloneConfiguration standaloneConfiguration,
        JedisClientConfiguration clientConfiguration) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(standaloneConfiguration, clientConfiguration);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean
    @Autowired
    public <T> RedisTemplate<String, T> customRedisTemplate(Class<T> clazz) {
        RedisSerializer keySerializer = RedisSerializer.string();
        FastJsonRedisSerializer<T> valueSerializer = new FastJsonRedisSerializer(clazz);

        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);

        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        template.setConnectionFactory(connectionFactory(standaloneConfiguration(), clientConfiguration()));
        template.afterPropertiesSet();
        return template;
    }
}
