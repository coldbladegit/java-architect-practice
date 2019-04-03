package com.cold.blade.architect.datastructure.tree;

import java.util.Collection;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 完全二叉树：若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数， 第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public final class CompleteBinaryTree<T> {

    private final ReentrantLock lock = new ReentrantLock();

    // 完全二叉树的根节点
    private CompleteBinaryTreeNode root = TreeNodes.emptyCompleteBinaryTreeNode();
    private LongAdder nodeCount = new LongAdder();
    private LongAdder hierarchy = new LongAdder();

    CompleteBinaryTree(Collection<T> data) {
        // 默认树的高度为1
        hierarchy.increment();
        init(data);
    }

    public BinaryTreeNode insert(T data) {
        return null;
    }

    public void remove(BinaryTreeNode node) {

    }

    public boolean isEmpty() {
        // 根节点无数据表示这是一颗空树
        return nodeCount.longValue() == 0;
    }

    private void init(Collection data) {
        if (null == data || data.isEmpty()) {
            return;
        }
    }

    private void insert(CompleteBinaryTreeNode parent, T data) {
        CompleteBinaryTreeNode child = TreeNodes.newCompleteBinaryTreeNode(parent.getHierarchy() + 1, data);
        //
        if (null == parent.getLeftChild()) {
            parent.setLeftChild(child);
        } else {
            // 建立兄弟关系
            CompleteBinaryTreeNode leftChild = (CompleteBinaryTreeNode) parent.getLeftChild();
            leftChild.setBrother(child);
            child.setBrother(leftChild);

            parent.setRightChild(child);
        }
        nodeCount.increment();
    }

    private CompleteBinaryTreeNode searchNewNodeParent() {
        if (root == TreeNodes.emptyCompleteBinaryTreeNode()) {
            root = TreeNodes.newCompleteBinaryTreeNode();
            return root;
        }
        double fullBinaryTreeNodeCount = Math.pow(2, hierarchy.doubleValue()) - 1;
        if (nodeCount.doubleValue() == fullBinaryTreeNodeCount) {

        } else {

        }
        return null;
    }
}
