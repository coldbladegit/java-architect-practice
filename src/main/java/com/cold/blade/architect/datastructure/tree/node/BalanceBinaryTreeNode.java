package com.cold.blade.architect.datastructure.tree.node;

import java.util.Objects;

/**
 * 平衡二叉树节点
 */
public class BalanceBinaryTreeNode<T extends Comparable> {

    public static final int LEFT_HIGHER = 1;
    public static final int EQUAL_HEIGHT = 0;
    public static final int RIGHT_HIGHER = -1;

    private BalanceBinaryTreeNode parent;
    private BalanceBinaryTreeNode leftChild;
    private BalanceBinaryTreeNode rightChild;
    private int balanceFactor = EQUAL_HEIGHT;
    private T datum;

    public BalanceBinaryTreeNode(T datum) {
        this.datum = datum;
    }

    public BalanceBinaryTreeNode parent() {
        return this.parent;
    }

    public BalanceBinaryTreeNode parent(BalanceBinaryTreeNode parent) {
        this.parent = parent;
        return this;
    }

    public BalanceBinaryTreeNode leftChild() {
        return this.leftChild;
    }

    public BalanceBinaryTreeNode leftChild(BalanceBinaryTreeNode leftChild) {
        this.leftChild = leftChild;
        leftChild.parent = this;
        return this;
    }

    public BalanceBinaryTreeNode rightChild() {
        return this.rightChild;
    }

    public BalanceBinaryTreeNode rightChild(BalanceBinaryTreeNode rightChild) {
        this.rightChild = rightChild;
        rightChild.parent = this;
        return this;
    }

    public BalanceBinaryTreeNode removeLeftChild() {
        if (Objects.nonNull(leftChild)) {
            leftChild.parent = null;
            leftChild = null;
        }
        return this;
    }

    public BalanceBinaryTreeNode removeRightChild() {
        if (Objects.nonNull(rightChild)) {
            rightChild.parent = null;
            rightChild = null;
        }
        return this;
    }

    public T datum() {
        return this.datum;
    }

    public BalanceBinaryTreeNode datum(T datum) {
        this.datum = datum;
        return this;
    }

    public int balanceFactor() {
        return this.balanceFactor;
    }

    public BalanceBinaryTreeNode balanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
        return this;
    }

    public boolean isLeaf() {
        return Objects.isNull(leftChild) && Objects.isNull(rightChild);
    }

    public boolean isFull() {
        return Objects.nonNull(leftChild) && Objects.nonNull(rightChild);
    }
}
