package com.cold.blade.redis.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cold.blade.redis.BaseTest;

/**
 * @author cold_blade
 * @version 1.0
 */
public class EventDelayServiceTest extends BaseTest {

    @Autowired
    private EventDelayService delay;

    @Test
    public void testDelay() throws InterruptedException {
        JSONObject obj = new JSONObject();
        obj.put("name", "cold_blade");
        delay.add(obj, 1, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(200);
        Set<JSONObject> results = delay.fetch();
        Assert.assertTrue(results.isEmpty());
        TimeUnit.SECONDS.sleep(2);
        results = delay.fetch();
        Assert.assertFalse(results.isEmpty());
        Assert.assertTrue(delay.fetch().isEmpty());
    }
}
