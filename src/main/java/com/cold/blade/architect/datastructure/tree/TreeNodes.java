package com.cold.blade.architect.datastructure.tree;

import com.cold.blade.architect.datastructure.tree.node.BalanceBinaryTreeNode;
import com.cold.blade.architect.datastructure.tree.node.CompleteBinaryTreeNode;

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
}
