package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.MergeSort;

/**
 * @Description 归并排序的单元测试
 *
 * @author cold_blade
 * @date 2020/8/17
 * @version 1.0
 */
public class MergeSortTest extends BaseTest {

    @Test
    public void test() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        MergeSort.sort(arr);
        Assert.assertTrue(arr[0] == 1);
        Assert.assertTrue(arr[8] == 9);
    }
}
