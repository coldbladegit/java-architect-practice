package com.cold.blade.architect.datastructure.tree;

/**
 * @author cold_blade
 * @version 1.0
 * @description
 * @date 2019/4/26
 */
public interface BinaryTreeNode<T> extends TreeNode<T> {

    BinaryTreeNode getParent();

    void setParent(BinaryTreeNode parent);

    BinaryTreeNode getLeftChild();

    void setLeftChild(BinaryTreeNode child);

    void removeLeftChild();

    BinaryTreeNode getRightChild();

    void setRightChild(BinaryTreeNode child);

    void removeRightChild();
}
