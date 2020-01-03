package com.cold.blade.redis.service;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cold.blade.redis.BaseTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedisCacheServiceTest extends BaseTest {

    @Autowired
    private RedisCacheService cacheService;
    private long expire = 600;
    private TimeUnit unit = TimeUnit.SECONDS;

    @Test
    public void testSet() {
        String key = "cold_blade_str";
        String strValue = "12";
        cacheService.set(key, strValue, expire, unit);
        Assert.assertEquals(strValue, cacheService.getString(key).orElse(null));

        key = "cold_blade_byte";
        Byte byteValue = 9;
        cacheService.set(key, byteValue, expire, unit);
        cacheService.increment(key);
        Assert.assertEquals(Byte.valueOf((byte) 10), cacheService.getByte(key).orElse(null));

        key = "cold_blade_int";
        Integer intValue = 10;
        cacheService.set(key, intValue, expire, unit);
        cacheService.increment(key);
        Assert.assertEquals(Integer.valueOf(11), cacheService.getInt(key).orElse(null));

        key = "cold_blade_long";
        Long longValue = 11L;
        cacheService.set(key, longValue, expire, unit);
        cacheService.increment(key);
        Assert.assertEquals(Long.valueOf(12), cacheService.getLong(key).orElse(null));

        key = "cold_blade_obj";
        cacheService.set(key, CacheData.builder().name("test").age(11).build(), expire, unit);
        CacheData cacheData = cacheService.get(key, CacheData.class).orElse(null);
        Assert.assertNotNull(cacheData);
        Assert.assertEquals("test", cacheData.name);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CacheData {

        private String name;
        private int age;
    }
}
