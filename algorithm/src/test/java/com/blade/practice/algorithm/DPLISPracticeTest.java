package com.blade.practice.algorithm;

import org.junit.Assert;
import org.junit.Test;

import com.blade.practice.BaseTest;

import cold.blade.practice.algorithm.DPLISPractice;

/**
 * @Description 动态规划思路解决数组最长单调递增子序列的问题的单元测试
 *
 * @author cold_blade
 * @date 2020/10/29
 * @version 1.0
 */
public class DPLISPracticeTest extends BaseTest {

    @Test
    public void lengthTest() {
        Assert.assertTrue(DPLISPractice.length(new int[0]) == 0);
        Assert.assertTrue(DPLISPractice.length(null) == 0);
        Assert.assertTrue(DPLISPractice.length(new int[]{5, 6, 7, 1, 2, 8}) == 4);
    }
}
