package com.cold.blade.architect.datastructure.tree;

import java.util.Objects;

import com.cold.blade.architect.datastructure.tree.node.BalanceBinaryTreeNode;

import lombok.AllArgsConstructor;
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
            root = TreeNodes.newBalanceBinaryTreeNode(datum);
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
        BalanceBinaryTreeNode leftChild = node.leftChild();
        // 左左情况
        if (leftChild.balanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            leftChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            return rotateRight(node);
        }
        // 左右情况
        BalanceBinaryTreeNode rightChild = leftChild.rightChild();
        if (rightChild.balanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.balanceFactor(BalanceBinaryTreeNode.RIGHT_HIGHER);
            leftChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        } else if (rightChild.balanceFactor() == BalanceBinaryTreeNode.EQUAL_HEIGHT) {
            node.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            leftChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        } else {
            node.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            leftChild.balanceFactor(BalanceBinaryTreeNode.LEFT_HIGHER);
        }
        // rightChild 最终会成为子树的新根节点
        rightChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        // 先以leftChild节点进行左旋
        rotateLeft(leftChild);
        // 再以node节点进行右旋
        return rotateRight(node);
    }

    /**
     * 对根节点为{@code node}的二叉树做右平衡(右子树高)处理
     *
     * @param node 待右平衡旋转子树的根节点
     * @return 新根节点
     */
    protected BalanceBinaryTreeNode rightBalance(BalanceBinaryTreeNode node) {
        BalanceBinaryTreeNode rightChild = node.rightChild();
        // 右右的情况
        if (rightChild.balanceFactor() == BalanceBinaryTreeNode.RIGHT_HIGHER) {
            return rotateLeft(node);
        }
        // 右左的情况
        BalanceBinaryTreeNode leftChild = rightChild.leftChild();
        if (leftChild.balanceFactor() == BalanceBinaryTreeNode.LEFT_HIGHER) {
            node.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            rightChild.balanceFactor(BalanceBinaryTreeNode.RIGHT_HIGHER);
        } else if (leftChild.balanceFactor() == BalanceBinaryTreeNode.EQUAL_HEIGHT) {
            node.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
            rightChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        } else {
            node.balanceFactor(BalanceBinaryTreeNode.LEFT_HIGHER);
            rightChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        }
        // 右子树的左子节点最终会成为当前子二叉树的新根节点(平衡状态)
        leftChild.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
        rotateRight(leftChild);
        return rotateLeft(node);
    }

    private BalanceBinaryTreeNode rotateRight(BalanceBinaryTreeNode node) {
        return rotate(node, false);
    }

    private BalanceBinaryTreeNode rotateLeft(BalanceBinaryTreeNode node) {
        return rotate(node, true);
    }

    /**
     * 旋转操作，返回当前子树的新根节点
     *
     * @param node 待旋转子树的根节点
     * @param isLeftRotation 左右旋标记
     * @return 新根节点
     */
    protected BalanceBinaryTreeNode rotate(BalanceBinaryTreeNode node, boolean isLeftRotation) {
        BalanceBinaryTreeNode parent = node.parent();
        BalanceBinaryTreeNode child;
        if (isLeftRotation) {
            child = node.rightChild();
            // node右子节点的左子节点成为node新的右子节点
            node.rightChild(child.leftChild());
            // node的父节点成为其右子节点的父节点,node成为其右子节点的新左子节点
            child.parent(parent).leftChild(node);
        } else {
            child = node.leftChild();
            // node左子节点的右子节点成为node新的左子节点
            node.leftChild(child.rightChild());
            // node的父节点成为其左子节点的父节点,node成为其左子节点的新右子节点
            child.parent(parent).rightChild(node);
        }
        return child;
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
            if (datum.equals(node.datum())) {
                return null;
            }
            if (node.datum().compareTo(datum) < 0) {
                if (Objects.isNull(node.rightChild())) {
                    return ActionResult.builder()
                        .node(node.rightChild(TreeNodes.newBalanceBinaryTreeNode(datum)))
                        .parent(node).isLeft(false).build();
                }
                node = node.rightChild();
            } else {
                if (Objects.isNull(node.leftChild())) {
                    return ActionResult.builder()
                        .node(node.leftChild(TreeNodes.newBalanceBinaryTreeNode(datum)))
                        .parent(node).isLeft(true).build();
                }
                node = node.leftChild();
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
            if (datum.equals(node.datum())) {
                break;
            }
            if (node.datum().compareTo(datum) < 0) {
                node = node.rightChild();
            } else {
                node = node.leftChild();
            }
        } while (Objects.nonNull(node));

        if (Objects.isNull(node)) {
            return null;
        } else if (node.isLeaf()) {
            return node;
        } else {
            // 若将删除节点拥有两个子节点的问题转换为只有一个子节点的问题
            BalanceBinaryTreeNode deletedNode = node;
            do {
                if (deletedNode.balanceFactor() == BalanceBinaryTreeNode.RIGHT_HIGHER) {
                    deletedNode = deletedNode.rightChild();
                } else {
                    deletedNode = deletedNode.leftChild();
                }
            } while (deletedNode.isFull());
            node.datum(deletedNode.datum());
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
        BalanceBinaryTreeNode parent = node.parent();
        if (Objects.isNull(parent)) {
            root = null;
            return ActionResult.builder().build();
        }
        BalanceBinaryTreeNode child = Objects.nonNull(node.leftChild()) ? node.leftChild() : node.rightChild();
        boolean isLeft = node.equals(parent.leftChild());
        if (isLeft) {
            parent.removeLeftChild().leftChild(child);
        } else {
            parent.removeRightChild().rightChild(child);
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
            balanceFactor = parent.balanceFactor();
            if (BalanceBinaryTreeNode.RIGHT_HIGHER == balanceFactor) {
                if (isLeft ^ isDelete) {
                    parent.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
                } else {
                    leftBalance(parent);
                }
                return;
            } else if (BalanceBinaryTreeNode.LEFT_HIGHER == balanceFactor) {
                if (isLeft ^ isDelete) {
                    rightBalance(parent);
                } else {
                    parent.balanceFactor(BalanceBinaryTreeNode.EQUAL_HEIGHT);
                }
                return;
            }
            parent.balanceFactor(isLeft ^ isDelete ? BalanceBinaryTreeNode.LEFT_HIGHER : BalanceBinaryTreeNode.RIGHT_HIGHER);
            if (Objects.nonNull(parent.parent())) {
                isLeft = parent.equals(parent.parent().leftChild());
            }
            parent = parent.parent();
        } while (Objects.nonNull(parent));
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ActionResult {

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
