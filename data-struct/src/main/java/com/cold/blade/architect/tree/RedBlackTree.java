package com.cold.blade.architect.tree;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.cold.blade.architect.tree.node.RedBlackTreeNode;
import com.cold.blade.architect.tree.node.TreeNodes;
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
        doBalanceAfterInsert(leafNode);
        return leafNode;
    }

    public RedBlackTreeNode delete(T datum) {
        RedBlackTreeNode node = search(datum).orElseThrow(() -> new IllegalStateException("not found"));
        if (node.isFull()) {
            node = replaceNode(node);
        }
        RedBlackTreeNode child = (RedBlackTreeNode) (node.leftChild().isLeaf() ? node.rightChild() : node.leftChild());
        doDelete(node, child);
        if (node.isBlack()) {
            if (child.isRed()) {
                child.flipColor();
            } else {
                doBalanceAfterDelete(child);
            }
        }

        return node;
    }

    private void doDelete(RedBlackTreeNode node, RedBlackTreeNode child) {
        RedBlackTreeNode parent = (RedBlackTreeNode) node.parent();
        node.removeAllChildren();
        if (Objects.nonNull(parent)) {
            parent.replaceChild(node, child);
        } else {
            root = null;
        }
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

    private void doBalanceAfterInsert(RedBlackTreeNode node) {
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

    private Optional<RedBlackTreeNode> search(T datum) {
        RedBlackTreeNode node = root;
        do {
            if (node.isEqual(datum)) {
                return Optional.of(node);
            }
            node = (RedBlackTreeNode) (node.isLess(datum) ? node.leftChild() : node.rightChild());
        } while (!node.isLeaf());
        return Optional.empty();
    }

    private RedBlackTreeNode replaceNode(RedBlackTreeNode node) {
        RedBlackTreeNode deleteNode;
        Function<RedBlackTreeNode, RedBlackTreeNode> func;
        if (((RedBlackTreeNode) node.leftChild()).isRed()) {
            // 左子节点为红色，寻找左子树的最大节点
            deleteNode = (RedBlackTreeNode) node.leftChild();
            func = (RedBlackTreeNode redBlackTreeNode) -> (RedBlackTreeNode) redBlackTreeNode.rightChild();
        } else {
            // 寻找右子树的最小节点
            deleteNode = (RedBlackTreeNode) node.rightChild();
            func = (RedBlackTreeNode redBlackTreeNode) -> (RedBlackTreeNode) redBlackTreeNode.leftChild();
        }
        while (func.apply(deleteNode).isLeaf()) {
            deleteNode = func.apply(deleteNode);
        }
        node.datum(deleteNode.datum());

        return deleteNode;
    }

    /**
     * 前置条件：node为叶节点（由删除节点的特性决定：最多有一个非叶节点的子节点）
     * @param node 待删除节点的子节点
     */
    private void doBalanceAfterDelete(RedBlackTreeNode node) {
        do {
            RedBlackTreeNode parent = (RedBlackTreeNode) node.parent();
            RedBlackTreeNode sibling = node.sibling();
            if (Objects.isNull(parent)) {
                node.flipColor(node.isRed());
                break;
            }
            if (sibling.isRed()) {
                // parent以及sibling的子节点都为黑色（性质4），交换parent和sibling的颜色并以parent进行旋转
                BinaryTreeRotator rotator = parent.isLeftChild(node) ? BinaryTreeRotator.LEFT_ROTATOR : BinaryTreeRotator.RIGHT_ROTATOR;
                flipColorAndRotate(rotator, parent, parent, sibling);
                sibling = node.sibling();// 指向新的sibling(黑)
            }
            // 以下情形的sibling均为黑色(性质4)
            if (parent.isBlack() && ((RedBlackTreeNode) sibling.leftChild()).isBlack()
                && ((RedBlackTreeNode) sibling.rightChild()).isBlack()) {
                // sibling置为红色，parent左右分支的黑色节点数相等，但比parent的sibling分支少了一个黑节点，递归判断
                sibling.flipColor();
                node = parent;
                continue;
            }
            if (parent.isRed() && ((RedBlackTreeNode) sibling.leftChild()).isBlack()
                && ((RedBlackTreeNode) sibling.rightChild()).isBlack()) {
                flipColor(parent, sibling);
                break;
            }
            if (parent.isLeftChild(node) && ((RedBlackTreeNode) sibling.leftChild()).isRed()
                && ((RedBlackTreeNode) sibling.rightChild()).isBlack()) {
                flipColorAndRotate(BinaryTreeRotator.RIGHT_ROTATOR, sibling, sibling, (RedBlackTreeNode) sibling.leftChild());
                sibling = node.sibling();
            }
            if (parent.isRightChild(node) && ((RedBlackTreeNode) sibling.leftChild()).isBlack()
                && ((RedBlackTreeNode) sibling.rightChild()).isRed()) {
                flipColorAndRotate(BinaryTreeRotator.LEFT_ROTATOR, sibling, sibling, (RedBlackTreeNode) sibling.rightChild());
                sibling = node.sibling();
            }
            // 只需判断node为parent左子节且sibling右子节点为红色或者node为parent右子节点且sibling左子节点为红色的情形，
            // parent置为黑色（如果必要），sibling的颜色替换为parent之前的颜色，并以parent做旋转，旋转后将sibling的红色子节点置为黑色
            // 在此等情形下：
            // 1、若parent之前为黑色，sibling（旋转之前）为黑色，旋转后node的新sibling经过sibling（之前）和parent两个黑节点，
            // 这与之前经过parent和sibling两个黑节点一致；
            // 2、parent之前为红色，则sibling（之前）为红色（原先parent的），旋转之后node的新sibling经过一红一黑两个节点，
            // 这与之前经过parent（红）与sibling（黑）一致，所以parent不管为红or黑都不影响这种情形下的操作
            sibling.copyColor(parent);
            parent.flipColor(parent.isRed());
            if (parent.isLeftChild(node)) {
                ((RedBlackTreeNode) sibling.rightChild()).flipColor();
                BinaryTreeRotator.LEFT_ROTATOR.rotate(parent);
            } else {
                ((RedBlackTreeNode) sibling.leftChild()).flipColor();
                BinaryTreeRotator.RIGHT_ROTATOR.rotate(parent);
            }
            break;
        } while (true);
    }

    private void dealRedParentCase(RedBlackTreeNode node, RedBlackTreeNode parent) {
        // 父节点为红色，则兄弟节点一定为黑色且为非叶节点（性质4和5决定）
        RedBlackTreeNode sibling = node.sibling();
        // 优先处理sibling的红色子节点与node在其父节点同侧的情况（需要旋转sibling），否则以parent旋转后会破坏性质4
        if (parent.isLeftChild(node) && ((RedBlackTreeNode) sibling.leftChild()).isRed()) {
            // 以sibling进行左旋并交换sibling及其左子节点的颜色
            flipColorAndRotate(BinaryTreeRotator.LEFT_ROTATOR, sibling, sibling, (RedBlackTreeNode) sibling.leftChild());
        } else if (parent.isRightChild(node) && ((RedBlackTreeNode) sibling.rightChild()).isRed()) {
            // 以sibling进行右旋并交换sibling及其右子节点的颜色
            flipColorAndRotate(BinaryTreeRotator.RIGHT_ROTATOR, sibling, sibling, (RedBlackTreeNode) sibling.rightChild());
        }
        // 以parent进行旋转，sibling成为子树的新根节点，填补了由于node被删除而破坏性质5
        if (parent.isLeftChild(node) && ((RedBlackTreeNode) sibling.leftChild()).isBlack()) {
            // 以parent进行左旋
            BinaryTreeRotator.LEFT_ROTATOR.rotate(parent);
        } else if (parent.isRightChild(node) && ((RedBlackTreeNode) sibling.rightChild()).isBlack()) {
            // 以parent进行右旋
            BinaryTreeRotator.RIGHT_ROTATOR.rotate(parent);
        }
    }

    /**
     * 前提条件：父节点为黑色
     * @param node 待删除节点
     * @param parent 待删除节点父节点
     */
    private void dealBlackParentCase(RedBlackTreeNode node, RedBlackTreeNode parent) {
        RedBlackTreeNode sibling = node.sibling();
        if (sibling.isRed()) {
            // sibling为红色，则其两个子节点为黑色(性质4)，交换sibling及其某个子节点的颜色，并以parent进行旋转
            // 让红子节点作为parent的某个子节点
            if (parent.isLeftChild(node)) {
                flipColor(sibling, (RedBlackTreeNode) sibling.leftChild());
                BinaryTreeRotator.LEFT_ROTATOR.rotate(parent);
            } else {
                flipColor(sibling, (RedBlackTreeNode) sibling.rightChild());
                BinaryTreeRotator.RIGHT_ROTATOR.rotate(parent);
            }
        } else {

        }
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
