package com.cold.blade.architect.datastructure.tree;

import java.util.Objects;

import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 平衡二叉树：它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/26
 */
public final class BalanceBinaryTree<T extends Comparable> {

    private BalanceBinaryTreeNode<T> root = null;

    public BalanceBinaryTreeNode insert(T datum) {
        if (Objects.nonNull(root)) {
            ActionResult result = doInsert(datum);
            if (Objects.nonNull(result)) {
                doBalance(result.parent, result.isLeft, false);
                return result.node;
            }
            return null;
        } else {
            root = TreeNodes.newBalanceBinaryTreeNode(1, datum);
            return root;
        }
    }

    public boolean delete(T datum) {
        if (isEmpty()) {
            return false;
        }
        BalanceBinaryTreeNode node = positionDeletedNode(datum);
        if (Objects.isNull(node)) {
            return false;
        }
        ActionResult result = doDelete(node);
        if (Objects.nonNull(result.parent)) {
            doBalance(result.parent, result.isLeft, true);
        }
        return true;
    }

    public boolean isEmpty() {
        return Objects.isNull(root);
    }

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
        } else if (rightChild.getBalanceFactor() == BalanceBinaryTreeNode.EQUAL_HEIGHT) {
            node.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
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
        BalanceBinaryTreeNode rightChild = (BalanceBinaryTreeNode) node.getRightChild();
        // 右右的情况
        if (rightChild.getBalanceFactor() == BalanceBinaryTreeNode.RIGHT_HIGHER) {
            return doLeftRotate(node);
        }
        // 右左的情况
        BalanceBinaryTreeNode leftChild = (BalanceBinaryTreeNode) rightChild.getLeftChild();
        if (leftChild.getBalanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            rightChild.setBalanceFactor(BalanceBinaryTreeNode.RIGHT_HIGHER);
        } else if (leftChild.getBalanceFactor() == BalanceBinaryTreeNode.EQUAL_HEIGHT) {
            node.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            rightChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        } else {
            node.setBalanceFactor(BalanceBinaryTreeNode.LEFT_HIGHER);
            rightChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        }
        // 右子树的左子节点最终会成为当前子二叉树的新根节点(平衡状态)
        leftChild.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        doRightRotation(leftChild);
        return doLeftRotate(node);
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

    /**
     * 插入新节点到正确位置，若数据已存在，则插入失败
     *
     * @param datum 新节点的数据
     * @return 成功返回{@code ActionResult}，失败返回null
     */
    private ActionResult doInsert(T datum) {
        BalanceBinaryTreeNode node = root;
        do {
            if (datum.equals(node.getDatum())) {
                return null;
            }
            if (((Comparable) node.getDatum()).compareTo(datum) < 0) {
                if (Objects.isNull(node.getRightChild())) {
                    return ActionResult.builder()
                        .node((BalanceBinaryTreeNode) node.setRightChild(
                            TreeNodes.newBalanceBinaryTreeNode(node.getHierarchy() + 1, datum)))
                        .parent(node).isLeft(false).build();
                }
                node = (BalanceBinaryTreeNode) node.getRightChild();
            } else {
                if (Objects.isNull(node.getLeftChild())) {
                    return ActionResult.builder()
                        .node((BalanceBinaryTreeNode) node.setLeftChild(
                            TreeNodes.newBalanceBinaryTreeNode(node.getHierarchy() + 1, datum)))
                        .parent(node).isLeft(true).build();
                }
                node = (BalanceBinaryTreeNode) node.getLeftChild();
            }
        } while (Objects.nonNull(node));
        return null;
    }

    /**
     * 定位待删除节点， 定位失败返回null
     *
     * @param datum 待删除节点的数据
     * @return 返回该节点OR null
     */
    private BalanceBinaryTreeNode positionDeletedNode(T datum) {
        BalanceBinaryTreeNode node = root;
        do {
            if (datum.equals(node.getDatum())) {
                break;
            }
            if (((Comparable) node.getDatum()).compareTo(datum) < 0) {
                node = (BalanceBinaryTreeNode) node.getRightChild();
            } else {
                node = (BalanceBinaryTreeNode) node.getLeftChild();
            }
        } while (Objects.nonNull(node));

        if (Objects.isNull(node)) {
            return null;
        } else if (node.isLeafNode()) {
            return node;
        } else {
            // 若将删除节点拥有两个子节点的问题转换为只有一个子节点的问题
            BalanceBinaryTreeNode deletedNode = node;
            do {
                if (deletedNode.getBalanceFactor() == BalanceBinaryTreeNode.RIGHT_HIGHER) {
                    deletedNode = (BalanceBinaryTreeNode) deletedNode.getRightChild();
                } else {
                    deletedNode = (BalanceBinaryTreeNode) deletedNode.getLeftChild();
                }
            } while (deletedNode.getChildCount() == 2);
            node.setDatum(deletedNode.getDatum());
            return deletedNode;
        }
    }

    /**
     * 删除节点
     *
     * @param node 待删除的节点
     * @return 返回{@code ActionResult}
     */
    private ActionResult doDelete(BalanceBinaryTreeNode node) {
        BalanceBinaryTreeNode parent = node.getParent();
        if (Objects.isNull(parent)) {
            root = null;
            return ActionResult.builder().build();
        }
        BalanceBinaryTreeNode childNode = (BalanceBinaryTreeNode) (Objects.nonNull(node.getLeftChild()) ? node.getLeftChild()
            : node.getRightChild());
        boolean isLeft = node.equals(parent.getLeftChild());
        if (isLeft) {
            parent.setLeftChild(childNode);
        } else {
            parent.setRightChild(childNode);
        }
        return ActionResult.builder().node(node).parent(parent).isLeft(isLeft).build();
    }

    /**
     * 因为插入是删除的反向操作，因此其对父节点的平衡因子的影响有一定的对称性
     *
     * @param parent 插入OR删除节点的父节点
     * @param isLeft 插入OR删除节点是否为父节点的左子节点
     * @param isDelete 是否为删除操作
     */
    private void doBalance(BalanceBinaryTreeNode parent, boolean isLeft, boolean isDelete) {
        int balanceFactor;
        do {
            balanceFactor = parent.getBalanceFactor();
            if (BalanceBinaryTreeNode.RIGHT_HIGHER == balanceFactor) {
                if (isLeft ^ isDelete) {
                    parent.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
                } else {
                    leftBalance(parent);
                }
                return;
            } else if (BalanceBinaryTreeNode.LEFT_HIGHER == balanceFactor) {
                if (isLeft ^ isDelete) {
                    rightBalance(parent);
                } else {
                    parent.setBalanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
                }
                return;
            }
            parent.setBalanceFactor(isLeft ^ isDelete ? BalanceBinaryTreeNode.LEFT_HIGHER : BalanceBinaryTreeNode.RIGHT_HIGHER);
            if (Objects.nonNull(parent.getParent())) {
                isLeft = parent.equals(parent.getParent().getLeftChild());
            }
            parent = parent.getParent();
        } while (Objects.nonNull(parent));
    }

    @Builder
    @NoArgsConstructor
    private class ActionResult {

        /**
         * 插入OR删除操作节点
         */
        BalanceBinaryTreeNode node;

        /**
         * 插入OR删除操作节点的父节点
         */
        BalanceBinaryTreeNode parent;
        /**
         * 插入OR删除操作节点在父节点所处位置
         */
        boolean isLeft;
    }
}
