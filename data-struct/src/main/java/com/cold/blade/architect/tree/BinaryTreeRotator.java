package com.cold.blade.architect.tree;

import java.util.Objects;

import com.cold.blade.architect.tree.node.BinaryTreeNode;

/**
 * 二叉树旋转器，用于平衡二叉树及其扩展的二叉树结构的旋转操作
 * @version 1.0
 */
public enum BinaryTreeRotator {
    LEFT_ROTATOR {
        @Override
        public void rotate(BinaryTreeNode node) {
            BinaryTreeNode parent = node.parent();
            BinaryTreeNode rightChild = node.rightChild();
            // 右子节点的左子节点作为node节点右子节点
            node.rightChild(rightChild.leftChild());
            // node节点作为新跟节点的左子节点
            rightChild.leftChild(node);
            // node节点的子节点作为子树新的根节点
            if (Objects.nonNull(parent)) {
                parent.replaceChild(node, rightChild);
            } else {
                // child节点为整棵树的根节点
                rightChild.removeParent();
            }
        }
    }, RIGHT_ROTATOR {
        @Override
        public void rotate(BinaryTreeNode node) {
            BinaryTreeNode parent = node.parent();
            BinaryTreeNode leftChild = node.leftChild();
            // 左子节点的右子节点作为当前节点左子节点
            node.leftChild(leftChild.rightChild());
            // node节点作为新跟节点的右子节点
            leftChild.rightChild(node);
            // node节点的子节点作为子树新的根节点
            if (Objects.nonNull(parent)) {
                parent.replaceChild(node, leftChild);
            } else {
                // child节点为整棵树的根节点
                leftChild.removeParent();
            }
        }
    };

    public abstract void rotate(BinaryTreeNode node);
}
