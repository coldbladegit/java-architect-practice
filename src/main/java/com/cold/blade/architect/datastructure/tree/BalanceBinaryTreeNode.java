package com.cold.blade.architect.datastructure.tree;

import lombok.Data;

/**
 * 平衡二叉树节点
 */
@Data
public class BalanceBinaryTreeNode<T> extends DefaultBinaryTreeNode<T> {

    public static final int LEFT_HIGHER = 1;
    public static final int EQUAL_HEIGHT = 0;
    public static final int RIGHT_HIGHER = -1;

    private int balanceFactor = EQUAL_HEIGHT;

    BalanceBinaryTreeNode(int hierarchy, T datum) {
        super(hierarchy, datum);
    }
}
