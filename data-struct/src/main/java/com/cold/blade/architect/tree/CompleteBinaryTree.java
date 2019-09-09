package com.cold.blade.architect.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cold.blade.architect.tree.node.CompleteBinaryTreeNode;
import com.cold.blade.architect.tree.node.TreeNodes;

/**
 * 完全二叉树：若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数， 第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。 Note: 由于完全二叉树不具备在删除节点的操作上的自我调整功能，因此只能保证在插入操作上的完全二叉树特性。
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
@Component
public final class CompleteBinaryTree<T> {

    // 完全二叉树的根节点,做为默认节点
    private final CompleteBinaryTreeNode<T> root = TreeNodes.newCompleteBinaryTreeNode();
    private int nodeCount = 1;

    public CompleteBinaryTreeNode insert(T datum) {
        if (Objects.isNull(root.datum())) {
            return root.datum(datum);
        }
        return doInsert(positionNewNode(), datum);
    }

    public void clear() {
        root.datum(null);
        root.leftChild(null).rightChild(null);
        nodeCount = 1;
    }

    public boolean isEmpty() {
        // 根节点无数据表示这是一颗空树
        return Objects.isNull(root.datum());
    }

    public int getHierarchy() {
        return (int) Math.ceil((Math.log(nodeCount + 1) / Math.log(2)));
    }

    public List<CompleteBinaryTreeNode> toArrayList() {
        List<CompleteBinaryTreeNode> nodes = new ArrayList<>(nodeCount);
        LinkedList<CompleteBinaryTreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        do {
            CompleteBinaryTreeNode node = queue.pop();
            nodes.add(node);
            if (Objects.nonNull(node.leftChild())) {
                queue.addLast(node.leftChild());
            }
            if (Objects.nonNull(node.rightChild())) {
                queue.addLast(node.rightChild());
            }
        } while (!queue.isEmpty());
        return nodes;
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    private CompleteBinaryTreeNode doInsert(CompleteBinaryTreeNode parent, T datum) {
        CompleteBinaryTreeNode child = TreeNodes.newCompleteBinaryTreeNode(datum);
        if (Objects.isNull(parent.leftChild())) {
            parent.leftChild(child);
        } else {
            parent.rightChild(child);
        }
        nodeCount++;
        return child;
    }

    /**
     * 广度优先遍历,获取待插入节点的父节点
     */
    private CompleteBinaryTreeNode positionNewNode() {
        LinkedList<CompleteBinaryTreeNode> nodes = new LinkedList<>();
        CompleteBinaryTreeNode parent = root;
        while (parent.isFull()) {
            nodes.addLast(parent.leftChild());
            nodes.addLast(parent.rightChild());
            parent = nodes.pop();
        }
        return parent;
    }
}
