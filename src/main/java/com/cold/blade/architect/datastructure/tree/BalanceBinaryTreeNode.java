package com.cold.blade.architect.datastructure.tree;

import lombok.Data;

/**
 * 平衡二叉树节点
 */
@Data
public class BalanceBinaryTreeNode<T> extends DefaultBinaryTreeNode<T> {

    private int balanceFactor;

    BalanceBinaryTreeNode(int hierarchy, T datum) {
        super(hierarchy, datum);
    }

    BalanceBinaryTreeNode updateBalanceFactor(BalanceBinaryTreeNode child) {
        if (child == getLeftChild()) {
            balanceFactor++;
        } else {
            balanceFactor--;
        }
        return this;
    }

    public boolean isUnbalanced() {
        return Math.abs(balanceFactor) >= 2;
    }
}
