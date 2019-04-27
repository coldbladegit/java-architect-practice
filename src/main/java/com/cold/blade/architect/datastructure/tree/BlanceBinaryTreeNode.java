package com.cold.blade.architect.datastructure.tree;

import lombok.Data;

/**
 * 平衡二叉树节点
 */
@Data
public class BlanceBinaryTreeNode<T> extends DefaultBinaryTreeNode<T> {

    private int leftChildHierarchy;
    private int rightChildHierarchy;

    public BlanceBinaryTreeNode(int hierarchy, T datum) {
        super(hierarchy, datum);
    }
}
