package com.cold.blade.reactor;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.BaseTest;


/**
 * @version 1.0
 */
public class StreamGenerateTest extends BaseTest {

    @Test
    public void testIterate() {
        int[] numbers = FibonacciSequence.iterate(5);
        Assert.assertEquals(5, numbers.length);
        Assert.assertTrue(Arrays.equals(new int[]{0, 1, 1, 2, 3}, numbers));

        Assert.assertEquals(5, FibonacciSequence.elementAt(5));
    }

    @Test
    public void testGenerate() {
        int[] numbers = FibonacciSequence.generate(5);
        Assert.assertEquals(5, numbers.length);
        Assert.assertTrue(Arrays.equals(new int[]{0, 1, 1, 2, 3}, numbers));
    }
}
