package com.cold.blade.architect.datastructure.tree;

import java.util.Objects;

/**
 * <p>
 *     红黑树：
 *     <li>
 *         <ol>节点是红色或黑色。</ol>
 *         <ol>根是黑色。</ol>
 *         <ol>所有叶子都是黑色（叶子是NIL节点）。</ol>
 *         <ol>每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）</ol>
 *         <ol>从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点</ol>
 *     </li>
 * </p>
 * @author cold_blade
 * @version 1.0
 */
public final class RedBlackTree<T extends Comparable> {

    private RedBlackTreeNode root = null;

    protected RedBlackTreeNode rotate(RedBlackTreeNode node, boolean isLeftRotation) {
        checkPrecondition(node);

        RedBlackTreeNode grandParent = node.grandParent();
        RedBlackTreeNode parent = node.parent();
        RedBlackTreeNode sibling = node.sibling();
        // 祖父节点的父节点作为新的祖父节点
        parent.parent(grandParent.parent());
        // 父节点作为祖父节点的新的父节点
        grandParent.parent(parent);
        // 兄、弟节点作为之前祖父节点的左/右子节点, 祖父节点作为父节点的左/右子节点
        if (isLeftRotation) {
            grandParent.rightChild(sibling);
            parent.leftChild(grandParent);
        } else {
            grandParent.leftChild(sibling);
            parent.rightChild(grandParent);
        }

        return node;
    }

    private void doBalance(RedBlackTreeNode node, boolean isDelete) {
        // TODO:
    }

    private void checkPrecondition(RedBlackTreeNode node) {
        // 左旋的前置条件：当前节点必须拥有父节点和祖父节点
        if (Objects.isNull(node.parent()) || Objects.isNull(node.grandParent())) {
            throw new IllegalStateException("rotating node is in wrong state, parent or grand parent is null");
        }
    }
}
