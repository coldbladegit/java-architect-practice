package com.cold.blade.architect.datastructure.tree.node;

/**
 * 提供一些static方法实例化各种树节点实例对象
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class TreeNodes {

    private TreeNodes() {
    }

    public static DefaultBinaryTreeNode newDefaultBinaryTreeNode() {
        return new DefaultBinaryTreeNode();
    }

    public static <T> CompleteBinaryTreeNode newCompleteBinaryTreeNode() {
        return new CompleteBinaryTreeNode<T>();
    }

    public static <T> CompleteBinaryTreeNode newCompleteBinaryTreeNode(T datum) {
        return new CompleteBinaryTreeNode(datum);
    }

    public static <T extends Comparable> BalanceBinaryTreeNode newBalanceBinaryTreeNode(T datum) {
        return new BalanceBinaryTreeNode(datum);
    }

    public static <T extends Comparable> RedBlackTreeNode newRedBlackTreeNode(T datum) {
        return new RedBlackTreeNode(datum);
    }

    public static <T extends Comparable> RedBlackTreeNode newLeafRedBlackTreeNode() {
        return new RedBlackTreeNode<T>().flipColor();
    }
}
