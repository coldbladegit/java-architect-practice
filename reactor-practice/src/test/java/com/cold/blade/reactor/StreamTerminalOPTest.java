package com.cold.blade.reactor;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.BaseTest;
import com.google.common.base.Stopwatch;

/**
 * @version 1.0
 */
public class StreamTerminalOPTest extends BaseTest {

    @Test
    public void testSyncReduce() {
        int result = IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b);
        Assert.assertEquals(2 * 3 * 4 * 5, result);
        result = IntStream.rangeClosed(1, 5).reduce(0, Math::min);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testParallelReduce() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long syncResult = LongStream.rangeClosed(0, Integer.MAX_VALUE).reduce(0, Long::sum);
        long syncElapse = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("sync cost time: " + syncElapse);
        stopwatch = Stopwatch.createStarted();
        long asyncResult = LongStream.rangeClosed(0, Integer.MAX_VALUE).parallel().reduce(0, Long::sum);
        long asyncElapse = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.println("async cost time: " + asyncElapse);

        Assert.assertEquals(syncResult, asyncResult);
        Assert.assertTrue(syncElapse > asyncElapse);
    }
}
