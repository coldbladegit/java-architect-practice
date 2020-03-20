package com.cold.blade.redis.service;

import java.util.Optional;

/**
 * 为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
 * 1、互斥性。在任意时刻，只有一个客户端能持有锁。
 * 2、不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * 3、具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * 4、解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 *
 * @author cold_blade
 * @version 1.0
 */
public interface RedisLockService {

    /**
     * 获取redis锁
     *
     * @param key 锁的key
     * @return 成功返回当次请求锁的requestId(UUID)，失败返回空
     */
    Optional<String> lock(String key);

    /**
     * 获取redis锁
     *
     * @param key 锁的key
     * @param lockExpireMillis 锁的过期时间
     * @return 成功返回当次请求锁的requestId(UUID)，失败返回空
     */
    Optional<String> lock(String key, long lockExpireMillis);

    /**
     * 尝试在一段时间内去获取锁
     *
     * @param key 锁的key
     * @param tryExpireMillis 尝试获取锁的最大等待时间（单位毫秒），超过该时间未获取到锁返回失败
     * @return 成功返回当次请求锁的requestId(UUID)，失败返回空
     */
    Optional<String> tryLock(String key, long tryExpireMillis);

    /**
     * 尝试在一段时间内去获取锁
     * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
     *
     * @param key 锁的key
     * @param tryExpireMillis 尝试获取锁的最大等待时间（单位毫秒），超过该时间未获取到锁返回失败
     * @param lockExpireMillis 锁的过期时间
     * @return 成功返回当次请求锁的requestId(UUID)，失败返回空
     */
    Optional<String> tryLock(String key, long tryExpireMillis, long lockExpireMillis);

    /**
     * 解锁
     *
     * @param key 锁的key
     * @param requestId 成功获取锁时返回的requestId
     * @return 成功返回true，失败返回false
     */
    boolean unLock(String key, String requestId);
}
