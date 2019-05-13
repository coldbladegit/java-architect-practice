package com.cold.blade.architect.datastructure.tree;

import lombok.Data;
import lombok.NonNull;

/**
 * 平衡二叉树节点
 */
@Data
public class BalanceBinaryTreeNode<T> extends DefaultBinaryTreeNode<T> {

    public static final int LEFT_HIGHER = 1;
    public static final int EQUAL_HEIGHT = 0;
    public static final int RIGHT_HIGHER = -1;

    private int balanceFactor = EQUAL_HEIGHT;
    private BalanceBinaryTreeNode parent;

    BalanceBinaryTreeNode(T datum) {
        super(1, datum);
    }

    BalanceBinaryTreeNode(int hierarchy, T datum) {
        super(hierarchy, datum);
    }

    /**
     * 移除当前节点的左/右子节点
     *
     * @param child 左/右子节点
     * @return 返回当前节点
     */
    public BalanceBinaryTreeNode removeChild(@NonNull BalanceBinaryTreeNode child) {
        if (child.equals(getLeftChild())) {
            setLeftChild(null);
        } else if (child.equals(getRightChild())) {
            setRightChild(null);
        }
        return this;
    }
}
