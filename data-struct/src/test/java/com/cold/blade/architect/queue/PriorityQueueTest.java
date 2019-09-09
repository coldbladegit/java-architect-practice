package com.cold.blade.architect.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;

/**
 * @author cold_blade
 * @version 1.0
 */
public class PriorityQueueTest extends BaseTest {

    private PriorityQueue<Integer> numbers;

    @Override
    public void setUp() {
        super.setUp();
        numbers = new PriorityQueue<>(11, Integer::compareTo);
    }

    @Test
    public void testOffer() {
        IntStream.rangeClosed(1, 5).boxed()
            .sorted(Comparator.reverseOrder())
            .forEach(i -> numbers.offer(i));
        int e = 1;
        while (!numbers.isEmpty()) {
            Assert.assertEquals(e++, numbers.poll().intValue());
        }
    }
}
