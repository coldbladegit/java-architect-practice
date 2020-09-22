package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.RadixSort;

/**
 * @Description 基数排序的单元测试
 *
 * @author cold_blade
 * @date 2020/9/22
 * @version 1.0
 */
public class RadixSortTest extends BaseTest {

    @Test
    public void test() {
        int[] array = { 2314, 5428, 373, 2222, 17 };
        RadixSort.sort(array);
        Assert.assertEquals(17, array[0]);
        Assert.assertEquals(2222, array[2]);
        Assert.assertEquals(5428, array[4]);
    }
}
