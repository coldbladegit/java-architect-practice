package com.cold.blade.architect.datastructure.immutable.tree;

import java.util.Objects;

import com.cold.blade.architect.datastructure.tree.BinaryTreeNode;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/26
 */
@Immutable
public final class ImmutableBinaryTreeNode<T> implements BinaryTreeNode {

    private final BinaryTreeNode leftChild;
    private final BinaryTreeNode rightChild;
    private final int hierarchy;

    // data的单数形式
    private final T datum;

    private ImmutableBinaryTreeNode(BinaryTreeNode leftChild, BinaryTreeNode rightChild, int hierarchy, T datum) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.hierarchy = hierarchy;
        this.datum = datum;
    }

    public static BinaryTreeNodeBuilder builder() {
        return new BinaryTreeNodeBuilder();
    }

    @Override
    public int getChildCount() {
        int cnt = 0;
        if (Objects.nonNull(leftChild)) {
            cnt++;
        }
        if (Objects.nonNull(rightChild)) {
            cnt++;
        }
        return cnt;
    }

    @Override
    public Object getDatum() {
        return this.datum;
    }

    @Override
    public void setDatum(Object datum) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public boolean isLeafNode() {
        return getChildCount() == 0;
    }

    @Override
    public int getHierarchy() {
        return this.hierarchy;
    }

    @Override
    public void setHierarchy(int hierarchy) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public BinaryTreeNode getLeftChild() {
        return this.leftChild;
    }

    @Override
    public BinaryTreeNode setLeftChild(BinaryTreeNode child) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    @Override
    public BinaryTreeNode getRightChild() {
        return this.rightChild;
    }

    @Override
    public BinaryTreeNode setRightChild(BinaryTreeNode child) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    public static class BinaryTreeNodeBuilder<T> {

        private BinaryTreeNode leftChild;
        private BinaryTreeNode rightChild;
        private int hierarchy;

        // data的单数形式
        private T datum;

        public BinaryTreeNodeBuilder() {
        }

        public BinaryTreeNodeBuilder leftChild(BinaryTreeNode leftChild) {
            this.leftChild = leftChild;
            return this;
        }

        public BinaryTreeNodeBuilder rightChild(BinaryTreeNode rightChild) {
            this.rightChild = rightChild;
            return this;
        }

        public BinaryTreeNodeBuilder hierarchy(int hierarchy) {
            this.hierarchy = hierarchy;
            return this;
        }

        public BinaryTreeNodeBuilder datum(T datum) {
            this.datum = datum;
            return this;
        }

        public ImmutableBinaryTreeNode build() {
            return new ImmutableBinaryTreeNode(leftChild, rightChild, hierarchy, datum);
        }
    }
}
