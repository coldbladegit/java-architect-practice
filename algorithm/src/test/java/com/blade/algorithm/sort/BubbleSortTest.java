package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.BubbleSort;

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
        BubbleSort<Integer> sort = new BubbleSort<>();
        sort.sort(integers);
        Assert.assertTrue(1 == integers[0]);
        Assert.assertTrue(9 == integers[5]);
    }
}
