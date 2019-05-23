package com.cold.blade.architect.datastructure.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedBlackTreeTest extends BaseTest {

    private RedBlackTree<Integer> redBlackTree;

    @Override
    public void setUp() {
        super.setUp();
        redBlackTree = TreeFactory.newRedBlackTree();
    }

    @Test
    public void rotate() {
        List<RedBlackTreeNode<Integer>> nodes = new ArrayList<>(5);
        IntStream.rangeClosed(1, 5).forEach(datum -> nodes.add(TreeNodes.newRedBlackTreeNode(datum)));
        RedBlackTreeNode grandParent = nodes.get(0);
        RedBlackTreeNode uncle = nodes.get(1);
        RedBlackTreeNode parent = nodes.get(2);
        RedBlackTreeNode sibling = nodes.get(3);
        RedBlackTreeNode node = nodes.get(4);
        grandParent.leftChild(uncle);
        grandParent.rightChild(parent);
        parent.leftChild(sibling);
        parent.rightChild(node);
        redBlackTree.rotate(node, true);
        Assert.assertTrue(Objects.isNull(node.grandParent()));
        Assert.assertTrue(node.sibling().equals(grandParent));

        grandParent.parent(null);
        grandParent.leftChild(parent);
        grandParent.rightChild(uncle);
        parent.leftChild(node);
        parent.rightChild(sibling);
        redBlackTree.rotate(node, false);
        Assert.assertTrue(Objects.isNull(node.grandParent()));
        Assert.assertTrue(node.sibling().equals(grandParent));
    }
}
