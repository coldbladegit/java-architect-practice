package com.blade.practice;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @version 1.0
 * @description
 * @date 2019/4/26
 */
@RunWith(SpringRunner.class)
@Slf4j
public abstract class BaseTest {

    @Before
    public void setUp() {
        log.info("===UNIT_TEST=== do something before test");
    }

    @After
    public void tearDown() {
        log.info("===UNIT_TEST=== do something after test");
    }
}
