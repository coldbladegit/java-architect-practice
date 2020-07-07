package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.SelectionSort;

/**
 * @Description 选择排序的单元测试
 *
 * @author cold_blade
 * @date 2020/7/6
 * @version 1.0
 */
public class SelectionSortTest extends BaseTest {

    @Test
    public void sortTest() {
        Integer[] integers = {5, 2, 8, 4, 9, 1};
        SelectionSort<Integer> sorter = new SelectionSort<>();
        sorter.sort(integers);
        Assert.assertTrue(1 == integers[0]);
        Assert.assertTrue(9 == integers[5]);
    }

    @Test
    public void reverseTest() {
        Integer[] integers = {5, 2, 8, 4, 9, 1};
        SelectionSort<Integer> sorter = new SelectionSort<>();
        sorter.reverse(integers);
        Assert.assertTrue(9 == integers[0]);
        Assert.assertTrue(1 == integers[5]);
    }
}
