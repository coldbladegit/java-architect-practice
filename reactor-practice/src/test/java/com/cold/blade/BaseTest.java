package com.cold.blade;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
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
