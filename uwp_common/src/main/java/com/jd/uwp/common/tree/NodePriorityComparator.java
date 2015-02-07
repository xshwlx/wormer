package com.jd.uwp.common.tree;

import com.jd.uwp.common.tree.builder.JsTreeNode;

import java.util.Comparator;

/**
 * 节点比较器
 *
 * @author xu shiheng
 */
class NodePriorityComparator implements Comparator<Object> {
    // 按照 （节点权值+节点编号） 比较
    public int compare(Object o1, Object o2) {
        // 按权值由大到小排序
        int w1 = ((JsTreeNode) o1).getPosition();
        int w2 = ((JsTreeNode) o2).getPosition();
        if (w1 < w2) {
            return 1;
        } else if (w1 > w2) {
            return -1;
        } else { // 权值相等时，按照节点编号由小到大排序
            int i1 = Integer.parseInt(((JsTreeNode) o1).getId());
            int i2 = Integer.parseInt(((JsTreeNode) o2).getId());
            return i1 < i2 ? -1 : (i1 == i2 ? 0 : 1);
        }
    }
}
