package com.cold.blade.architect.datastructure.tree;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;
import com.cold.blade.architect.datastructure.tree.node.CompleteBinaryTreeNode;

public class CompleteBinaryTreeTest extends BaseTest {

    private CompleteBinaryTree<Integer> completeBinaryTree;

    @Override
    public void setUp() {
        super.setUp();
        completeBinaryTree = TreeFactory.newCompleteBinaryTree();
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(completeBinaryTree.isEmpty());
    }

    @Test
    public void insert() {
        IntStream.rangeClosed(1, 5).forEach(completeBinaryTree::insert);
        Assert.assertEquals(5, completeBinaryTree.getNodeCount());
        List<CompleteBinaryTreeNode> nodes = completeBinaryTree.toArrayList();
        AtomicInteger datum = new AtomicInteger(1);
        nodes.forEach(node -> Assert.assertTrue(node.datum().equals(datum.getAndIncrement())));
    }

    @Test
    public void getHierarchy() {
        IntStream.range(1, 10).forEach(completeBinaryTree::insert);
        Assert.assertEquals(4, completeBinaryTree.getHierarchy());
    }
}
