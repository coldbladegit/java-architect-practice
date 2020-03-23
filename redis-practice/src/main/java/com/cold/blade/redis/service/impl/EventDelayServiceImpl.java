package com.cold.blade.redis.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cold.blade.redis.service.EventDelayService;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import redis.clients.jedis.Jedis;

/**
 * @author cold_blade
 * @version 1.0
 */
@Component
public class EventDelayServiceImpl implements EventDelayService, InitializingBean {

    private static final String DELAY_EVENT_KEY = "event_delay_key";

    @Autowired
    private RedisTemplate<String, JSONObject> redisTemplate;
    private ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(1,
        new ThreadFactoryBuilder().setNameFormat("redis_delay_queue_%s").setDaemon(true).build(),
        new CallerRunsPolicy());

    @Override
    public void add(JSONObject value, long delay, TimeUnit timeUnit) {
        double score = System.currentTimeMillis() + timeUnit.toMillis(delay);
        redisTemplate.opsForZSet().add(DELAY_EVENT_KEY, value, score);
    }

    @Override
    public Set<JSONObject> fetch() {
        return fetchDelayEvent();
    }

    @Override
    public void afterPropertiesSet() {
        scheduled.scheduleWithFixedDelay(this::fetchDelayEvent, 5, 5, TimeUnit.SECONDS);
    }

    private Set<JSONObject> fetchDelayEvent() {
        String command = "local result = redis.call('zrangeByScore',KEYS[1],ARGV[1],ARGV[2]) "
            + "if result ~= nil then redis.call('zremrangeByScore',KEYS[1],ARGV[1],ARGV[2]) end "
            + "return result";
        long now = System.currentTimeMillis();
        List<String> result = redisTemplate
            .execute((RedisCallback<List<String>>) redisConnection -> (List<String>) ((Jedis) redisConnection.getNativeConnection())
                .eval(command, Collections.singletonList(DELAY_EVENT_KEY), Lists.newArrayList("0", String.valueOf(now))));
        if (Objects.nonNull(result)) {
            return result.stream().map(JSON::parseObject).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
