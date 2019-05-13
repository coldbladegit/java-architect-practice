package com.cold.blade.architect.datastructure.tree;

/**
 * 二叉树树节点数据模型：包含父节点、左右子节点以及层级信息等
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public class DefaultBinaryTreeNode<T> implements BinaryTreeNode<T> {

    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;
    private int hierarchy;

    // data的单数形式
    private T datum;

    DefaultBinaryTreeNode(int hierarchy, T datum) {
        this.hierarchy = hierarchy;
        this.datum = datum;
    }

    @Override
    public BinaryTreeNode getRightChild() {
        return this.rightChild;
    }

    @Override
    public BinaryTreeNode setRightChild(BinaryTreeNode child) {
        this.rightChild = child;
        return child;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
        return this.leftChild;
    }

    @Override
    public BinaryTreeNode setLeftChild(BinaryTreeNode child) {
        this.leftChild = child;
        return child;
    }

    @Override
    public int getChildCount() {
        int cnt = 0;
        if (null != leftChild) {
            cnt++;
        }
        if (null != rightChild) {
            cnt++;
        }
        return cnt;
    }

    @Override
    public T getDatum() {
        return this.datum;
    }

    @Override
    public void setDatum(T datum) {
        this.datum = datum;
    }

    @Override
    public boolean isLeafNode() {
        return getChildCount() == 0;
    }

    @Override
    public int getHierarchy() {
        return this.hierarchy;
    }

    @Override
    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }
}
