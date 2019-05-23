package com.cold.blade.architect.datastructure.tree;

import java.util.Objects;

/**
 * @author cold_blade
 * @version 1.0
 */
public class RedBlackTreeNode<T extends Comparable> {

    private RedBlackTreeNode parent;
    private RedBlackTreeNode leftChild;
    private RedBlackTreeNode rightChild;
    private boolean isRed = true;
    private T datum;

    public RedBlackTreeNode(T datum) {
        this.datum = datum;
    }

    public RedBlackTreeNode grandParent() {
        return Objects.nonNull(parent) ? parent.parent() : null;
    }

    public RedBlackTreeNode parent() {
        return parent;
    }

    public RedBlackTreeNode parent(RedBlackTreeNode parent) {
        this.parent = parent;
        return this;
    }

    public RedBlackTreeNode uncle() {
        RedBlackTreeNode grandParent = grandParent();
        if (Objects.isNull(grandParent)) {
            return null;
        }
        return parent.equals(grandParent.leftChild) ? grandParent.rightChild : grandParent.leftChild;
    }

    public RedBlackTreeNode sibling() {
        if (Objects.isNull(parent)) {
            return null;
        }
        return this.equals(parent.leftChild) ? parent.rightChild : parent.leftChild;
    }

    public RedBlackTreeNode leftChild() {
        return this.leftChild;
    }

    public RedBlackTreeNode leftChild(RedBlackTreeNode leftChild) {
        this.leftChild = leftChild;
        leftChild.parent(this);
        return this;
    }

    public RedBlackTreeNode rightChild() {
        return this.rightChild;
    }

    public RedBlackTreeNode rightChild(RedBlackTreeNode rightChild) {
        this.rightChild = rightChild;
        rightChild.parent(this);
        return this;
    }

    public void flipColor() {
        this.isRed = !this.isRed;
    }

    public boolean isRed() {
        return isRed;
    }

    public T datum() {
        return this.datum;
    }

    public RedBlackTreeNode datum(T datum) {
        this.datum = datum;
        return this;
    }
}
