package com.cold.blade.architect.datastructure.tree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 完全二叉树节点模型
 *
 * @author cold_blade
 * @version 1.0
 * @date 2019/4/3
 */
@NoArgsConstructor
public final class CompleteBinaryTreeNode<T> extends BinaryTreeNode<T> {

    @Getter
    @Setter
    private CompleteBinaryTreeNode brother;

    CompleteBinaryTreeNode(int hierarchy, T datum) {
        setHierarchy(hierarchy);
        setDatum(datum);
    }
}
