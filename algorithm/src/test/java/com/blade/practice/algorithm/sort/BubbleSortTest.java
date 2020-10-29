package com.blade.practice.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.practice.BaseTest;

import cold.blade.practice.algorithm.sort.BubbleSort;

/**
 * @Description 冒泡排序的单元测试
 *
 * @author cold_blade
 * @date 2020/7/7
 * @version 1.0
 */
public class BubbleSortTest extends BaseTest {

    @Test
    public void testSort() {
        Integer[] integers = {5, 2, 8, 4, 9, 1};
        BubbleSort<Integer> sorter = new BubbleSort<>();
        sorter.sort(integers);
        Assert.assertTrue(1 == integers[0]);
        Assert.assertTrue(9 == integers[5]);
    }
}
