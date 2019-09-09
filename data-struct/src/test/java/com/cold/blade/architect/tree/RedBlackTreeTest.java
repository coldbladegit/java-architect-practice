package com.cold.blade.architect.tree;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cold.blade.architect.BaseTest;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedBlackTreeTest extends BaseTest {

    @Autowired
    private RedBlackTree<Integer> redBlackTree;

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void insert() {
        redBlackTree.insert(5);
        System.out.println("test");
    }
}
