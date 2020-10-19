package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.HeapSort;

/**
 * @Description 堆排序的单元测试
 *
 * @author cold_blade
 * @date 2020/8/18
 * @version 1.0
 */
public class HeapSortTest extends BaseTest {

    @Test
    public void test() {
        int[] arr = {15, 10, 30, 35, 25, 20, 40, 45, 50};
        HeapSort.sort(arr);
        Assert.assertTrue(arr[0] == 10);
        Assert.assertTrue(arr[8] == 50);
    }
}
