package com.cold.blade.architect.datastructure.tree;

/**
 * 提供一些static方法实例化各种树节点实例对象
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class TreeNodes {

    private static final BinaryTreeNode EMPTY_BINARY_TREE_NODE = new BinaryTreeNode(1, null);

    private TreeNodes() {
    }

    public static BinaryTreeNode emptyBinaryTreeNode() {
        return EMPTY_BINARY_TREE_NODE;
    }

    public static <T> BinaryTreeNode newBinaryTreeNode(int hierarchy, T datum) {
        return new BinaryTreeNode<>(hierarchy, datum);
    }
}
