package com.cold.blade.architect.datastructure.tree;

import com.cold.blade.architect.datastructure.immutable.tree.ImmutableBinaryTreeNode;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * 提供一些static方法实例化各种树节点实例对象
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class TreeNodes {

    /**
     * 静态常量，不可变节点
     */
    private static final BinaryTreeNode EMPTY_BINARY_TREE_NODE = ImmutableBinaryTreeNode.builder().hierarchy(1).build();

    private TreeNodes() {
    }

    @Immutable
    public static BinaryTreeNode emptyBinaryTreeNode() {
        return EMPTY_BINARY_TREE_NODE;
    }

    public static <T> BinaryTreeNode newBinaryTreeNode(int hierarchy, T datum) {
        return new DefaultBinaryTreeNode<>(hierarchy, datum);
    }

    public static <T> BalanceBinaryTreeNode newBalanceBinaryTreeNode(int hierarchy, T datum) {
        return new BalanceBinaryTreeNode<>(hierarchy, datum);
    }

    public static <T> BalanceBinaryTreeNode newBalanceBinaryTreeNode(T datum) {
        return new BalanceBinaryTreeNode<>(datum);
    }
}
