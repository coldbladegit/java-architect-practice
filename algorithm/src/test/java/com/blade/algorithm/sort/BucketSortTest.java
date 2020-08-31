package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.BucketSort;

/**
 * @Description 桶排序的单元测试
 *
 * @author cold_blade
 * @date 2020/8/31
 * @version 1.0
 */
public class BucketSortTest extends BaseTest {

    @Test
    public void test() {
        int[] array = {8, 7, 11, 51, 50, 76, 25, 98, 41, 68};
        BucketSort.sort(array);
        Assert.assertEquals(7, array[0]);
        Assert.assertEquals(76, array[array.length - 2]);
        Assert.assertEquals(98, array[array.length - 1]);
    }
}
