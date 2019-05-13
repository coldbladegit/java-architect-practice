package com.cold.blade.architect.datastructure.tree;

/**
 * @author cold_blade
 * @version 1.0
 * @description
 * @date 2019/4/26
 */
public interface BinaryTreeNode<T> extends TreeNode<T> {

    BinaryTreeNode getLeftChild();

    BinaryTreeNode setLeftChild(BinaryTreeNode child);

    BinaryTreeNode getRightChild();

    BinaryTreeNode setRightChild(BinaryTreeNode child);
}
