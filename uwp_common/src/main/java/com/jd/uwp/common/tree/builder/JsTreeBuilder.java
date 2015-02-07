package com.jd.uwp.common.tree.builder;

import com.jd.uwp.common.tree.AbstractTreeNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 树形结构创建
 *
 * @author xu shiheng
 */
public class JsTreeBuilder extends AbstractTreeBuilder {
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
        JsTreeNode root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator<?> it = dataList.iterator(); it.hasNext(); ) {
            Map<?, ?> dataRecord = (Map<?, ?>) it.next();
            AbstractTreeNode node = new JsTreeNode();
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
            JsTreeNode node = (JsTreeNode) ((Map.Entry) it.next()).getValue();
            if (node.getParentId() == null || node.getParentId().equals("") || node.getId().equals("0")) {
                root = node;
            } else {
                ((JsTreeNode) nodeList.get(node.getParentId())).addChild(node);
                // 在节点中增加一个父节点的引用
                node.setParentNode((JsTreeNode) nodeList.get(node.getParentId()));
            }
        }

        return root;
    }

    /**
     * 获取热点功能叶子
     *
     * @param functionLeafList
     * @return
     */
    public List<JsTreeNode> getHotFunctionLeaf(List<?> functionLeafList) {
        int count = 0;
        int totalWeight = 0;
        BigDecimal avgWeight;
        for (Iterator<?> it = functionLeafList.iterator(); it.hasNext(); ) {
            JsTreeNode node = (JsTreeNode) it.next();
            totalWeight += node.getPosition();
            count++;
        }
        avgWeight = (new BigDecimal(totalWeight)).divide(new BigDecimal(count),
                2, BigDecimal.ROUND_HALF_UP);
        List<JsTreeNode> retList = new ArrayList<JsTreeNode>();
        for (Iterator<?> it = functionLeafList.iterator(); it.hasNext(); ) {
            JsTreeNode node = (JsTreeNode) it.next();
            if (node.getPosition() > avgWeight.doubleValue()) {
                retList.add(node);
            }
        }
        return retList;
    }

}


