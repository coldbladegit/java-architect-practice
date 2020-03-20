package com.cold.blade.redis.service;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cold.blade.redis.BaseTest;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedisLockServiceTest extends BaseTest {

    @Autowired
    private RedisLockService service;

    @Test
    public void testUnlock() {
        String key = "cold_blade";
        String requestId = service.lock(key).orElse(null);
        Assert.assertTrue(Objects.nonNull(requestId));
        Assert.assertTrue(service.unLock(key, "1"));
    }
}
