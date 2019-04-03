package com.cold.blade.architect.datastructure.tree;

/**
 * 提供一些static方法实例化各种树节点实例对象
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class TreeNodes {

    private static final CompleteBinaryTreeNode EMPTY_COMPLETE_BINARY_TREE_NODE = new CompleteBinaryTreeNode();

    private TreeNodes() {
    }

    public static CompleteBinaryTreeNode emptyCompleteBinaryTreeNode() {
        return EMPTY_COMPLETE_BINARY_TREE_NODE;
    }

    public static <T> CompleteBinaryTreeNode newCompleteBinaryTreeNode() {
        return new CompleteBinaryTreeNode<T>();
    }

    public static <T> CompleteBinaryTreeNode newCompleteBinaryTreeNode(int hierarchy, T datum) {
        return new CompleteBinaryTreeNode<T>(hierarchy, datum);
    }
}
