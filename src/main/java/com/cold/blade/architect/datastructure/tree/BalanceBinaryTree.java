package com.cold.blade.architect.datastructure.tree;

/**
 * 平衡二叉树：它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/26
 */
public final class BalanceBinaryTree<T> {

    private BalanceBinaryTreeNode root = null;

    /**
     * 对根节点为{@code node}的二叉树做左平衡(左子树高)处理
     *
     * @param node 待左平衡旋转子树的根节点
     * @return 新根节点
     */
    protected BalanceBinaryTreeNode leftBalance(BalanceBinaryTreeNode node) {
        BalanceBinaryTreeNode leftChild = (BalanceBinaryTreeNode) node.getLeftChild();
        // 左左情况
        if (leftChild.getBalanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            leftChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            return doRightRotation(node);
        }
        // 左右情况
        BalanceBinaryTreeNode rightChild = (BalanceBinaryTreeNode) leftChild.getRightChild();
        if (rightChild.getBalanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.setBalanceFactor(BalanceBinaryTreeNode.RIGHT_HIGHER);
            leftChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        } else {
            node.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            leftChild.setBalanceFactor(BalanceBinaryTreeNode.LEFT_HIGHER);
        }
        // rightChild 最终会成为子树的新根节点
        rightChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        // 先以leftChild节点进行左旋
        doLeftRotate(leftChild);
        // 再以node节点进行右旋
        return doRightRotation(node);
    }

    /**
     * 对根节点为{@code node}的二叉树做右平衡(右子树高)处理
     *
     * @param node 待右平衡旋转子树的根节点
     * @return 新根节点
     */
    protected BalanceBinaryTreeNode rightBalance(BalanceBinaryTreeNode node) {
        // TODO
        return null;
    }

    /**
     * 左旋，该操作完成后，返回当前子树的新根节点
     *
     * @param node 待旋转子树的根节点
     * @return 新根节点
     */
    private BalanceBinaryTreeNode doLeftRotate(BalanceBinaryTreeNode node) {
        // 获取当前节点的右子节点
        BalanceBinaryTreeNode rightChild = (BalanceBinaryTreeNode) node.getRightChild();
        // 当前节点的右子节点设置为右子节点的左子节点
        node.setRightChild(rightChild.getLeftChild());
        // 右子节点的左子节点设置为当前节点
        rightChild.setLeftChild(node);

        return rightChild;
    }

    /**
     * 右旋，该操作完成后，返回当前子树的新根节点
     *
     * @param node 待旋转子树的根节点
     * @return 新根节点
     */
    private BalanceBinaryTreeNode doRightRotation(BalanceBinaryTreeNode node) {
        // 获取当前节点的左子节点
        BalanceBinaryTreeNode leftChild = (BalanceBinaryTreeNode) node.getLeftChild();
        // 当前节点的左子节点设置为左子节点的右子节点
        node.setLeftChild(leftChild.getRightChild());
        // 左子节点的右子节点设置为当前节点
        leftChild.setRightChild(node);

        return leftChild;
    }
}
