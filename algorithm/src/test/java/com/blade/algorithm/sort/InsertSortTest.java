package com.blade.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import com.blade.algorithm.BaseTest;

import cold.blade.algorithm.sort.InsertSort;

/**
 * @Description 插入排序的单元测试
 *
 * @author cold_blade
 * @date 2020/7/7
 * @version 1.0
 */
public class InsertSortTest extends BaseTest {

    @Test
    public void testSort() {
        Integer[] integers = {5, 2, 8, 4, 9, 1};
        InsertSort<Integer> sorter = new InsertSort<>();
        sorter.sort(integers);
        Assert.assertTrue(1 == integers[0]);
        Assert.assertTrue(9 == integers[5]);
    }
}
