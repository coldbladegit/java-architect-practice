package com.cold.blade.architect.datastructure.tree;

import java.util.LinkedList;
import java.util.Objects;

import lombok.Getter;

/**
 * 完全二叉树：若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数， 第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。 Note: 由于完全二叉树不具备在删除节点的操作上的自我调整功能，因此只能保证在插入操作上的完全二叉树特性。
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
@Getter
public final class CompleteBinaryTree<T> {

    // 完全二叉树的根节点
    private BinaryTreeNode root = TreeNodes.emptyBinaryTreeNode();
    private int nodeCount = 1;

    public BinaryTreeNode insert(T data) {
        return doInsert(positionNewNode(), data);
    }

    public boolean isEmpty() {
        // 根节点无数据表示这是一颗空树
        return Objects.isNull(root.getDatum());
    }

    public int getHierarchy() {
        return (int) Math.ceil((Math.log(nodeCount + 1) / Math.log(2)));
    }
    
    private BinaryTreeNode doInsert(BinaryTreeNode parent, T data) {
        BinaryTreeNode child = TreeNodes.newBinaryTreeNode(parent.getHierarchy() + 1, data);
        if (null == parent.getLeftChild()) {
            parent.setLeftChild(child);
        } else {
            parent.setRightChild(child);
        }
        nodeCount++;
        return child;
    }

    /**
     * 广度优先遍历,获取待插入节点的父节点
     */
    private BinaryTreeNode positionNewNode() {
        LinkedList<BinaryTreeNode> nodes = new LinkedList<>();
        BinaryTreeNode parent = root;
        while (Objects.nonNull(parent.getLeftChild()) && Objects.nonNull(parent.getRightChild())) {
            nodes.addLast(parent.getLeftChild());
            nodes.addLast(parent.getRightChild());
            parent = nodes.pop();
        }
        return parent;
    }
}
