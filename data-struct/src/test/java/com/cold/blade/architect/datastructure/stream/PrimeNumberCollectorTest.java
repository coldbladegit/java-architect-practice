package com.cold.blade.architect.datastructure.stream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cold.blade.architect.BaseTest;
import com.cold.blade.architect.stream.PrimeNumberCollector;
import com.google.common.base.Stopwatch;

/**
 * @version 1.0
 */
public class PrimeNumberCollectorTest extends BaseTest {

    @Autowired
    private PrimeNumberCollector collector;

    @Test
    public void testPrimeNumberCollector() {
        Map<Boolean, List<Integer>> numberMap = IntStream.rangeClosed(2, 50).boxed().collect(collector);
        numberMap.get(true).forEach(System.out::println);
    }

    @Test
    public void testCollectorEfficiency() {
        long customExecuteTime = getCollectorExecuteTime(collector);
        long partitionExecuteTime = getCollectorExecuteTime(Collectors.partitioningBy(this::isPrime));
        Assert.assertTrue(customExecuteTime < partitionExecuteTime);
        System.out.println("fast " + (partitionExecuteTime - customExecuteTime) * 1.0 / partitionExecuteTime);
    }

    private long getCollectorExecuteTime(Collector collector) {
        long minExecuteTime = Long.MAX_VALUE;
        for (int i = 0; i < 5; ++i) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            IntStream.rangeClosed(2, Integer.MAX_VALUE / 100).boxed().collect(collector);
            long curExecuteTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            if (curExecuteTime < minExecuteTime) {
                minExecuteTime = curExecuteTime;
            }
        }
        return minExecuteTime;
    }

    private boolean isPrime(int candidate) {
        int sqrt = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, sqrt).noneMatch(i -> candidate % i == 0);
    }
}
