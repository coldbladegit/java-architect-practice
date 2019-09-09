package com.cold.blade.architect.tree.node;

/**
 * 平衡二叉树节点
 */
public class BalanceBinaryTreeNode<T extends Comparable> extends DefaultBinaryTreeNode {

    public static final int LEFT_HIGHER = 1;
    public static final int EQUAL_HEIGHT = 0;
    public static final int RIGHT_HIGHER = -1;

    private int balanceFactor = EQUAL_HEIGHT;
    private T datum;

    public BalanceBinaryTreeNode(T datum) {
        this.datum = datum;
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
}
