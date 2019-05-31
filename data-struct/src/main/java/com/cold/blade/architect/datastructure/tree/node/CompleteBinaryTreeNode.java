package com.cold.blade.architect.datastructure.tree.node;

import java.util.Objects;

/**
 * @author cold_blade
 * @version 1.0
 */
public class CompleteBinaryTreeNode<T> {

    private CompleteBinaryTreeNode leftChild;
    private CompleteBinaryTreeNode rightChild;
    private T datum;

    public CompleteBinaryTreeNode() {
    }

    public CompleteBinaryTreeNode(T datum) {
        this.datum = datum;
    }

    public CompleteBinaryTreeNode leftChild() {
        return this.leftChild;
    }

    public CompleteBinaryTreeNode leftChild(CompleteBinaryTreeNode leftChild) {
        this.leftChild = leftChild;
        return this;
    }

    public CompleteBinaryTreeNode rightChild() {
        return this.rightChild;
    }

    public CompleteBinaryTreeNode rightChild(CompleteBinaryTreeNode rightChild) {
        this.rightChild = rightChild;
        return this;
    }

    public T datum() {
        return this.datum;
    }

    public CompleteBinaryTreeNode datum(T datum) {
        this.datum = datum;
        return this;
    }

    public boolean isFull() {
        return Objects.nonNull(leftChild) && Objects.nonNull(rightChild);
    }
}
