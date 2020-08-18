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
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        HeapSort.sort(arr);
        Assert.assertTrue(arr[0] == 1);
        Assert.assertTrue(arr[8] == 9);
    }
}
