package com.cold.blade.architect.datastructure.tree;


import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;

public class CompleteBinaryTreeTest extends BaseTest {

    private CompleteBinaryTree<Integer> completeBinaryTree;

    @Override
    public void setUp() {
        super.setUp();
        completeBinaryTree = TreeFactory.newCompleteBinaryTree();
        System.out.println(completeBinaryTree);
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(completeBinaryTree.isEmpty());
    }

    @Test
    public void insert() {
        Integer datum = 10;
        Assert.assertEquals(datum, completeBinaryTree.insert(10).getDatum());
    }

    @Test
    public void getHierarchy() {
        IntStream.range(1, 10).forEach(completeBinaryTree::insert);
        Assert.assertEquals(4, completeBinaryTree.getHierarchy());
    }
}
