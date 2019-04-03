package com.cold.blade.architect.datastructure.tree;

/**
 * 树节点的通用operation
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
public interface TreeNode<T> {

    TreeNode getParent();

    void setParent(TreeNode parent);

    int getChildCount();

    T getDatum();

    void setDatum(T datum);

    boolean isLeafNode();

    int getHierarchy();

    void setHierarchy(int hierarchy);
}
