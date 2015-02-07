package com.jd.uwp.common.tree.builder;

import com.jd.uwp.common.tree.AbstractTreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-16
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class ULTreeNode extends AbstractTreeNode {
    // 先序遍历，拼接JSON字符串
    public String toString() {

        if (visible) {
            String result = "";
            if (children != null && children.getSize() != 0) {
                result += "<li -- >";
                result += "<ul id= " + this.id + ">";
                result += children.toLi(children.getList().size() == 1);
                result += "</ul>";
                result += "</li -- >";
            } else {
                result = "<li id= " + this.id + ">";
                result += "</li>";
            }
            return result;
        } else {
            return "";
        }
    }
}
