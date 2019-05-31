package com.cold.blade.architect.datastructure.tree.node;

import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedBlackTreeNode<T extends Comparable> extends DefaultBinaryTreeNode {

    private boolean isRed = true;
    private T datum;

    RedBlackTreeNode() {
    }

    RedBlackTreeNode(T datum) {
        this.datum = datum;
    }

    public RedBlackTreeNode grandParent() {
        return Objects.nonNull(parent()) ? (RedBlackTreeNode) parent().parent() : null;
    }

    public RedBlackTreeNode uncle() {
        RedBlackTreeNode grandParent = grandParent();
        if (Objects.isNull(grandParent)) {
            return null;
        }
        return (RedBlackTreeNode) (parent().equals(grandParent.leftChild()) ? grandParent.rightChild() : grandParent.leftChild());
    }

    public RedBlackTreeNode sibling() {
        if (Objects.isNull(parent())) {
            return null;
        }
        return (RedBlackTreeNode) (this.equals(parent().leftChild()) ? parent().rightChild() : parent().leftChild());
    }

    public RedBlackTreeNode flipColor() {
        this.isRed = !this.isRed;
        return this;
    }

    public boolean isRed() {
        return isRed;
    }

    public boolean isBlack() {
        return !isRed;
    }

    public RedBlackTreeNode datum(T datum) {
        this.datum = datum;
        return this;
    }

    public boolean isLess(T datum) {
        Preconditions.checkNotNull(this.datum, "RedBlackNode's datum is null");
        return this.datum.compareTo(datum) < 0;
    }

    public boolean isEqual(T datum) {
        Preconditions.checkNotNull(this.datum, "RedBlackNode's datum is null");
        return this.datum.compareTo(datum) == 0;
    }

    @Override
    public boolean isLeaf() {
        return Objects.isNull(datum) && super.isLeaf();
    }

    public RedBlackTreeNode autoProduceLeafNode() {
        this.leftChild(TreeNodes.newLeafRedBlackTreeNode())
            .rightChild(TreeNodes.newLeafRedBlackTreeNode());
        return this;
    }

    public boolean isLeftChild(RedBlackTreeNode node) {
        return node.equals(leftChild());
    }

    public boolean isRightChild(RedBlackTreeNode node) {
        return node.equals(rightChild());
    }
}
