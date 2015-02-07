package com.jd.uwp.common.tree.builder;

import com.jd.uwp.common.tree.AbstractTreeNode;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-16
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class ULTreeBuilder extends AbstractTreeBuilder {
    /**
     * 构造加权多叉树
     *
     * @return 包含整棵树数据的根节点
     */
    @SuppressWarnings("rawtypes")
    public AbstractTreeNode buildWeightedMultiTree() {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap<String, AbstractTreeNode> nodeList = new LinkedHashMap<String, AbstractTreeNode>();
        // 根节点
        ULTreeNode root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator<?> it = dataList.iterator(); it.hasNext(); ) {
            Map<?, ?> dataRecord = (Map<?, ?>) it.next();
            AbstractTreeNode node = new ULTreeNode();
            node.setId((String) dataRecord.get("id"));
            node.setName((String) dataRecord.get("name"));
            node.setParentId((String) dataRecord.get("parentId"));
            node.setDescription((String) dataRecord.get("description"));
            node.setPosition(1);
            nodeList.put(node.getId(), node);
        }
        // 构造无序的多叉树
        Set<?> entrySet = nodeList.entrySet();
        for (Iterator<?> it = entrySet.iterator(); it.hasNext(); ) {
            ULTreeNode node = (ULTreeNode) ((Map.Entry) it.next()).getValue();
            if (node.getParentId() == null || node.getParentId().equals("") || node.getId().equals("0")) {
                root = node;
            } else {
                ((ULTreeNode) nodeList.get(node.getParentId())).addChild(node);
                // 在节点中增加一个父节点的引用
                node.setParentNode((ULTreeNode) nodeList.get(node.getParentId()));
            }
        }

        return root;
    }

}
