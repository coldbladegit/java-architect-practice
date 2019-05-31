package com.cold.blade.architect.datastructure.tree.node;

/**
 * @description
 * @author cold_blade
 * @date 2019/5/29
 * @version 1.0
 */
public interface BinaryTreeNode {

    BinaryTreeNode parent();

    BinaryTreeNode parent(BinaryTreeNode parent);

    BinaryTreeNode leftChild();

    BinaryTreeNode leftChild(BinaryTreeNode leftChild);

    BinaryTreeNode rightChild();

    BinaryTreeNode rightChild(BinaryTreeNode rightChild);

    BinaryTreeNode removeChild(BinaryTreeNode child);

    BinaryTreeNode removeParent();

    boolean isLeaf();

    boolean isFull();

    BinaryTreeNode replaceChild(BinaryTreeNode oldChild, BinaryTreeNode newChild);
}
