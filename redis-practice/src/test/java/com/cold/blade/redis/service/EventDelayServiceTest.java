package com.cold.blade.redis.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cold.blade.redis.BaseTest;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cold_blade
 * @version 1.0
 */
public class EventDelayServiceTest extends BaseTest {

    @Autowired
    private EventDelayService delay;

    @Test
    public void testDelay() throws InterruptedException {
        TestSerializable ts = new TestSerializable();
        ts.src = delay.getClass();
        ts.tgt = delay.getClass();
        ts.func = this::print;
        delay.add((JSONObject) JSON.toJSON(ts), 1, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(200);
        Set<JSONObject> results = delay.fetch();
        Assert.assertTrue(results.isEmpty());
        TimeUnit.SECONDS.sleep(2);
        results = delay.fetch();
        System.out.println(JSON.toJSONString(results));
        Assert.assertFalse(results.isEmpty());
        Assert.assertTrue(delay.fetch().isEmpty());
    }

    private String print(String string) {
        System.out.println(string);
        return string;
    }

    @Data
    @NoArgsConstructor
    public static class TestSerializable {

        private Class<? extends EventDelayService> src;
        private Class<? extends EventDelayService> tgt;
        private Function<String, String> func;
    }
}
