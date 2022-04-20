package com.blade.practice.algorithm;

import org.junit.Assert;
import org.junit.Test;

import com.blade.practice.BaseTest;

import cold.blade.practice.algorithm.LRUPractice;

/**
 * @Description LRU算法实现的单元测试
 *
 * @author cold_blade
 * @date 2022/4/19
 * @version 1.0
 */
public class LRUPracticeTest extends BaseTest {

    @Test
    public void test() {
        LRUPractice practice = new LRUPractice();
        practice.save(7);
        practice.save(1);
        practice.save(0);
        Assert.assertTrue(practice.lastValue().filter(val -> val == 0).isPresent());
        Assert.assertTrue(practice.firstValue().filter(val -> val == 7).isPresent());
        practice.get(7);
        Assert.assertTrue(practice.lastValue().filter(val -> val == 7).isPresent());
        practice.save(9);
        Assert.assertTrue(practice.lastValue().filter(val -> val == 9).isPresent());
        Assert.assertTrue(practice.firstValue().filter(val -> val == 0).isPresent());
        Assert.assertTrue(practice.size() == 3);
    }
}
