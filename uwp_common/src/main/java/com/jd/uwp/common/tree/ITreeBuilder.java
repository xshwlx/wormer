package com.jd.uwp.common.tree;

import java.util.List;

/**
 * 树形创建接口
 *
 * @author xu shiheng
 */
public interface ITreeBuilder {
    /**
     * 创建整棵树节点数据
     *
     * @return AbsTreeNode 树根节点
     */
    public AbstractTreeNode buileTree();

    /**
     * 构造加权多叉树
     *
     * @return AbsTreeNode
     */
    public AbstractTreeNode buildWeightedMultiTree();

    /**
     * 构造功能叶子树
     *
     * @param root
     * @return
     */
    public List<?> buildFunctionLeafList(AbstractTreeNode root);
}
