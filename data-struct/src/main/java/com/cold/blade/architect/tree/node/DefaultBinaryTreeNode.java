package com.cold.blade.architect.tree.node;

import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * @version 1.0
 */
public class DefaultBinaryTreeNode implements BinaryTreeNode {

    private BinaryTreeNode parent;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;

    @Override
    public BinaryTreeNode parent() {
        return parent;
    }

    @Override
    public BinaryTreeNode parent(BinaryTreeNode parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public BinaryTreeNode leftChild() {
        return leftChild;
    }

    @Override
    public BinaryTreeNode leftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
        if (Objects.nonNull(leftChild)) {
            this.leftChild.parent(this);
        }
        return this;
    }

    @Override
    public BinaryTreeNode rightChild() {
        return rightChild;
    }

    @Override
    public BinaryTreeNode rightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
        if (Objects.nonNull(rightChild)) {
            this.rightChild.parent(this);
        }
        return this;
    }

    @Override
    public BinaryTreeNode removeChild(BinaryTreeNode child) {
        Preconditions.checkNotNull(child);
        Preconditions.checkArgument(child.equals(leftChild) || child.equals(rightChild));

        if (child.equals(leftChild)) {
            leftChild.removeParent();
            leftChild = null;
        } else {
            rightChild.removeParent();
            rightChild = null;
        }

        return this;
    }

    @Override
    public BinaryTreeNode removeParent() {
        parent = null;
        return this;
    }

    @Override
    public boolean isLeaf() {
        return Objects.isNull(leftChild) && Objects.isNull(rightChild);
    }

    @Override
    public boolean isFull() {
        return Objects.nonNull(leftChild) && Objects.nonNull(rightChild);
    }

    @Override
    public BinaryTreeNode replaceChild(BinaryTreeNode oldChild, BinaryTreeNode newChild) {
        Preconditions.checkNotNull(oldChild);
        Preconditions.checkNotNull(newChild);
        Preconditions.checkState(oldChild.equals(leftChild) || oldChild.equals(rightChild));

        if (oldChild.equals(leftChild)) {
            leftChild(newChild);
        } else {
            rightChild(newChild);
        }
        oldChild.removeParent();

        return this;
    }
}
