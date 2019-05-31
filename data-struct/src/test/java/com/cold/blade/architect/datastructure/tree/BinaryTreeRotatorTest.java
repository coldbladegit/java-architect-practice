package com.cold.blade.architect.datastructure.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;
import com.cold.blade.architect.datastructure.tree.node.BinaryTreeNode;
import com.cold.blade.architect.datastructure.tree.node.TreeNodes;

/**
 * @version 1.0
 */
public class BinaryTreeRotatorTest extends BaseTest {

    @Test
    public void rotate() {
        List<BinaryTreeNode> nodes = new ArrayList<>(5);
        IntStream.rangeClosed(1, 5).forEach(datum -> nodes.add(TreeNodes.newDefaultBinaryTreeNode()));
        BinaryTreeNode grandParent = nodes.get(0);
        BinaryTreeNode uncle = nodes.get(1);
        BinaryTreeNode parent = nodes.get(2);
        BinaryTreeNode sibling = nodes.get(3);
        BinaryTreeNode node = nodes.get(4);
        grandParent.leftChild(uncle);
        grandParent.rightChild(parent);
        parent.leftChild(sibling);
        parent.rightChild(node);
        BinaryTreeRotator.LEFT_ROTATOR.rotate(grandParent);
        Assert.assertTrue(Objects.isNull(node.parent().parent()));
        Assert.assertTrue(node.parent().leftChild().equals(grandParent));
        Assert.assertTrue(grandParent.rightChild().equals(sibling));
    }
}
