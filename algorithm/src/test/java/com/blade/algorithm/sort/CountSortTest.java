package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.CountSort;

/**
 * @Description 计数排序的单元测试
 *
 * @author cold_blade
 * @date 2020/8/25
 * @version 1.0
 */
public class CountSortTest extends BaseTest {

    @Test
    public void test() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        CountSort.sort(arr);
        Assert.assertTrue(arr[0] == 1);
        Assert.assertTrue(arr[8] == 9);
    }
}
