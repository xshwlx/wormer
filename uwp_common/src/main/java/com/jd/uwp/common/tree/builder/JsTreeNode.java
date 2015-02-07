package com.jd.uwp.common.tree.builder;

import com.jd.uwp.common.tree.AbstractTreeNode;

/**
 * jstree 构建树节点类
 *
 * @author xu shiheng
 */
public class JsTreeNode extends AbstractTreeNode {

    // 先序遍历，拼接JSON字符串
    public String toString() {
        if (visible) {
            String result = "{" +
                    "\"attr\":{"
                    + "\"id\":\"" + id + "\""
                    + ", \"name\":\"" + name + "\""
                    + ", \"iconCls\":\"fa-folder\""
                    + ",\"description\" : \"" + description + "\"" +
                    "}" + ",\"data\":\"" + name + "\"";
            if (children != null && children.getSize() != 0) {
                result += ",\"children\":" + children.toJson();
            } else {
                result += ",\"leaf\":true";
            }
            return result + "}";
        } else {
            return "";
        }
    }

}
