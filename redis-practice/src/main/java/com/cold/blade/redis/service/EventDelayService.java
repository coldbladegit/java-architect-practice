package com.cold.blade.redis.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

/**
 * @author cold_blade
 * @version 1.0
 */
public interface EventDelayService {

    void add(JSONObject value, long delay, TimeUnit timeUnit);

    Set<JSONObject> fetch();
}
