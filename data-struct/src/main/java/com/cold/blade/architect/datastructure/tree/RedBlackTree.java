package com.cold.blade.architect.datastructure.tree;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cold.blade.architect.datastructure.tree.node.RedBlackTreeNode;
import com.cold.blade.architect.datastructure.tree.node.TreeNodes;
import com.google.common.base.Preconditions;

/**
 * <p>
 *     红黑树：
 *     <li>
 *         <ol>1.节点是红色或黑色。</ol>
 *         <ol>2.根是黑色。</ol>
 *         <ol>3.所有叶子都是黑色（叶子是NIL节点）。</ol>
 *         <ol>4.每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）</ol>
 *         <ol>5.从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点</ol>
 *     </li>
 * </p>
 * @author cold_blade
 * @version 1.0
 */
@Component
public final class RedBlackTree<T extends Comparable> {

    private RedBlackTreeNode root = null;

    public RedBlackTreeNode insert(T datum) {
        if (Objects.isNull(root)) {
            root = TreeNodes.newRedBlackTreeNode(datum);
            // 根节点黑色并自动生成叶节点
            return root.flipColor().autoProduceLeafNode();
        }
        RedBlackTreeNode leafNode = position(datum);
        // 直接使用当前叶节点，置为红色(新插入节点)并自动产生两个叶节点
        leafNode.datum(datum).flipColor().autoProduceLeafNode();
        // 自我平衡
        doBalance(leafNode);
        return leafNode;
    }

    /**
     * 定位当前数据的位置
     *
     * @param datum 当前数据
     * @return 返回当前数据的合适位置的叶节点OR抛异常
     */
    private RedBlackTreeNode position(T datum) {
        RedBlackTreeNode node = root;
        do {
            Preconditions.checkState(!node.isEqual(datum), "%s is already exist", datum);
            node = (RedBlackTreeNode) (node.isLess(datum) ? node.leftChild() : node.rightChild());
        } while (!node.isLeaf());
        return node;
    }

    private void doBalance(RedBlackTreeNode node) {
        do {
            RedBlackTreeNode parent = (RedBlackTreeNode) node.parent();
            if (Objects.isNull(parent)) {
                // 根节点，只需将颜色置为黑色
                node.flipColor(node.isRed());
                break;
            } else if (parent.isBlack()) {
                // 父节点为黑色，新插入节点为红色，没有破坏红黑树的性质
                break;
            } else if (parent.isRed() && (node.uncle().isRed())) {
                // 父节点和叔父节点均为红色(肯定有祖父节点，且祖父节点为黑色)，与祖父节点交换颜色，
                // 若祖父节点为根节点或祖父节点的父节点是红色，不满足性质2和4，将祖父节点当做新插入节点
                flipColor(parent, node.uncle(), node.grandParent());
                node = node.grandParent();
            } else if (parent.isLeftChild(node) && node.grandParent().isLeftChild(parent)) {
                // 当前节点是父节点的左子节点，父节点是祖父节点的左子节点，将父节点和祖父节点的颜色进行交换，
                // 从根节点到叔父分支的叶节点少了一个黑色节点不满足性质5，所以需进行右旋
                flipColorAndRotate(BinaryTreeRotator.RIGHT_ROTATOR, node.grandParent(), parent, node.grandParent());
                break;
            } else if (parent.isRightChild(node) && node.grandParent().isRightChild(parent)) {
                // 右右与左左情况对称
                flipColorAndRotate(BinaryTreeRotator.LEFT_ROTATOR, node.grandParent(), parent, node.grandParent());
                break;
            } else if (parent.isLeftChild(node) && node.grandParent().isRightChild(parent)) {
                // 当前节点是父节点的左子节点，父节点是祖父节点的右子节点，先转成右右的情况
                BinaryTreeRotator.RIGHT_ROTATOR.rotate(parent);
                node = parent;
            } else if (parent.isRightChild(node) && node.grandParent().isLeftChild(parent)) {
                // 当前节点是父节点的右子节点，父节点是祖父节点的左子节点，先转成左左的情况
                BinaryTreeRotator.LEFT_ROTATOR.rotate(parent);
                node = parent;
            }
        } while (true);
    }

    private void flipColorAndRotate(BinaryTreeRotator rotator, RedBlackTreeNode rotationalNode, RedBlackTreeNode... nodes) {
        flipColor(nodes);
        rotator.rotate(rotationalNode);
    }

    private void flipColor(RedBlackTreeNode... nodes) {
        Preconditions.checkNotNull(nodes);
        Arrays.stream(nodes).forEach(RedBlackTreeNode::flipColor);
    }
}
