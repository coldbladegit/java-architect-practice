package com.cold.blade.architect.datastructure.tree;

/**
 * 数据结构之Tree的工厂，用于生产各种常用的树实例对象
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class TreeFactory {

    private TreeFactory() {
    }

    public static CompleteBinaryTree newCompleteBinaryTree() {
        return new CompleteBinaryTree();
    }

    public static <T extends Comparable> RedBlackTree newRedBlackTree() {
        return new RedBlackTree<T>();
    }
}
