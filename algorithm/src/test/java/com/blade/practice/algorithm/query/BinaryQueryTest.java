package com.blade.practice.algorithm.query;

import org.junit.Assert;
import org.junit.Test;

import com.blade.practice.BaseTest;

import cold.blade.practice.algorithm.query.BinaryQuery;

/**
 * @Description 二分查找的单元测试
 *
 * @author cold_blade
 * @date 2020/9/24
 * @version 1.0
 */
public class BinaryQueryTest extends BaseTest {

    @Test
    public void test() {
        int[] array = {17, 373, 2222, 2314, 5428};
        int index = BinaryQuery.query(array, 17);
        Assert.assertEquals(0, index);
        index = BinaryQuery.query(array, 5428);
        Assert.assertEquals(4, index);
        index = BinaryQuery.query(array, 2222);
        Assert.assertEquals(2, index);
        index = BinaryQuery.query(array, 10);
        Assert.assertEquals(-1, index);
    }
}
