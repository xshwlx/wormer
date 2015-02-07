package com.jd.uwp.common.tree;

import com.jd.uwp.common.tree.builder.JsTreeNode;
import com.jd.uwp.common.tree.builder.ULTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 子节点列表
 *
 * @author xu shiheng
 */
@SuppressWarnings("rawtypes")
public class ChildrenNode {
    public List getList() {
        return list;
    }

    private List list = new ArrayList();

    public int getSize() {
        return list.size();
    }

    @SuppressWarnings("unchecked")
    public void addChild(AbstractTreeNode node) {
        list.add(node);
    }

    /**
     * 拼接子节点的JSON字符串
     */
    public String toJson() {
        String result = "[";
        for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
            JsTreeNode node = (JsTreeNode) it.next();
            if (node.isVisible()) {
                result += node.toString();
                result += ",";
            }
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }

    /**
     * 拼接子节点的JSON字符串
     */
    public String toLi(boolean b) {
        String result = "";
//        if(!b)
//        result  = "<li toli>";
        for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
            ULTreeNode node = (ULTreeNode) it.next();
            if (node.isVisible()) {
                result += node.toString();
            }
        }
//        if(!b)
//        result += "</li toli>";
        return result;
    }

    /**
     * 拼接子节点的JSON字符串
     */
    public String toLi() {
        String result = "";
        for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
            ULTreeNode node = (ULTreeNode) it.next();
            if (node.isVisible()) {
                result += node.toString();
            }
        }

        return result;
    }

    /**
     * 在孩子节点中寻找功能叶子节点
     *
     * @param leafList
     */
    public void initializeLeafList(List leafList) {
        for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
            ((AbstractTreeNode) it.next()).initializeLeafList(leafList);
        }
    }

    /**
     * 子节点排序
     */
    @SuppressWarnings("unchecked")
    public void sortChildren() {
        // 对本层节点进行排序
        // 可根据不同的排序属性，传入不同的比较器，这里传入优先级比较器
        Collections.sort(list, new NodePriorityComparator());
        // 对每个节点的下一层节点进行排序
        for (Iterator<?> it = list.iterator(); it.hasNext(); ) {
            ((JsTreeNode) it.next()).sortChildren();
        }
    }

    /**
     * 子节点为不可见
     */
    public void setTreeNotVisible() {
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((JsTreeNode) it.next()).setTreeNotVisible();
        }
    }

    /**
     * 设置孩子节点为可见
     */
    public void setTreeVisible() {
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((JsTreeNode) it.next()).setTreeVisible();
        }
    }

    /**
     * 搜索菜单节点，同时进行功能路径过滤
     *
     * @param keyWord
     */
    public void searchTreeNode(String keyWord) {
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            ((JsTreeNode) it.next()).searchTreeNode(keyWord);
        }
    }
}
