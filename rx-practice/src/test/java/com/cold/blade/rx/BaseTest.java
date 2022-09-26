package com.cold.blade.rx;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cold_blade
 * @version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {

    @Before
    public void setUp() {
        log.info("===test start===");
    }

    @After
    public void tearDown() {
        log.info("===test tear down===");
    }
}
