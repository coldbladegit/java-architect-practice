package com.cold.blade.architect.datastructure.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树树节点数据模型：包含父节点、左右子节点以及层级信息等
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BinaryTreeNode<T> implements TreeNode<T> {

    private BinaryTreeNode parent;
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;
    private int hierarchy;

    // data的单数形式
    private T datum;

    @Override
    public void setParent(TreeNode parent) {
        if (parent instanceof BinaryTreeNode) {
            this.parent = (BinaryTreeNode) parent;
        }
    }

    @Override
    public int getChildCount() {
        int cnt = 0;
        if (null != leftChild) {
            cnt++;
        }
        if (null != rightChild) {
            cnt++;
        }
        return cnt;
    }

    @Override
    public boolean isLeafNode() {
        return getChildCount() == 0;
    }
}
