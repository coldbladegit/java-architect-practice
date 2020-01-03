package com.cold.blade.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Before
    public void setUp() {
        flushRedis();
        log.info("===test start===");
    }

    @After
    public void tearDown() {
        log.info("===test tear down===");
        flushRedis();
    }

    private void flushRedis() {
        redisTemplate.execute((RedisCallback) (connection) -> {
            connection.flushDb();
            return true;
        });
    }
}
