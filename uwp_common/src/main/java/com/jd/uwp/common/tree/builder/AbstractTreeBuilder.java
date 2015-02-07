package com.jd.uwp.common.tree.builder;

import com.jd.uwp.common.tree.AbstractTreeNode;
import com.jd.uwp.common.tree.ITreeBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 树形结构创建
 *
 * @author xu shiheng
 */
public class AbstractTreeBuilder implements ITreeBuilder {
    /**
     * 散列数据列表
     */
    protected List<?> dataList;

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    /**
     * 子叶列表
     */
    protected List<?> functionLeafList;

    public List<?> getFunctionLeafList() {
        return functionLeafList;
    }

    public void setFunctionLeafList(List<?> functionLeafList) {
        this.functionLeafList = functionLeafList;
    }

    /**
     * 构造功能叶子列表
     *
     * @param root
     * @return
     */
    public List<?> buildFunctionLeafList(AbstractTreeNode root) {
        List<?> functionLeafList = new LinkedList<Object>();
        root.initializeLeafList(functionLeafList);
        return functionLeafList;
    }

    /**
     * 进行菜单节点搜索（即功能路径筛选）
     *
     * @param root
     * @param keyWord
     */
    public static void searchTreeNode(AbstractTreeNode root, String keyWord) {
        // 首先设置整棵树的功能路径为不可见
        root.setTreeNotVisible();
        // 在整棵功能树中搜索包含关键字的节点，并进行路径筛选
        root.searchTreeNode(keyWord);
    }

    /**
     * 增加功能路径权值
     *
     * @param root
     */
    public void increaseRouteWeight(AbstractTreeNode root, List<?> functionLeafList,
                                    String nodeId) {
        // 首先设置整棵树的功能路径为可见
        root.setTreeVisible();
        // 对包含功能叶子节点的路径权值加1
        for (Iterator<?> it = functionLeafList.iterator(); it.hasNext(); ) {
            AbstractTreeNode leafNode = (AbstractTreeNode) it.next();
            if (leafNode.getId().equals(nodeId)) {
                leafNode.increaseRouteWeight();
            }
        }
    }

    public AbstractTreeNode buileTree() {
        // 构造加权多叉树
        AbstractTreeNode root = this.buildWeightedMultiTree();
        // 构造功能叶子列表
        functionLeafList = this.buildFunctionLeafList(root);
        // 对多叉树重新进行横向排序
        //root.sortChildren();
        return root;
    }

    @Override
    public AbstractTreeNode buildWeightedMultiTree() {
        // TODO Auto-generated method stub
        return null;
    }

}


