package com.cold.blade.architect.datastructure.stream;

import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;

/**
 * @version 1.0
 */
public class StreamMiddleOPTest extends BaseTest {

    @Test
    public void testFilter() {
        Assert.assertTrue(IntStream.rangeClosed(1, 10).filter(i -> {
            System.out.println("filter i = " + i);
            return i < 5;
        }).noneMatch(i -> {
            System.out.println("match processor. i = " + i);
            return i > 5;
        }));
    }

    @Test
    public void testFlatMap() {
        long count = IntStream.rangeClosed(1, 5).boxed()
            .flatMap(i -> IntStream.rangeClosed(3, 5)
                .filter(j -> (i + j) % 4 == 0).boxed()
                .map(j -> new int[]{i, j})
            ).count();

        Assert.assertEquals(4, count);
    }
}
