package com.jd.uwp.common.tree;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 树形结构数据源构建
 *
 * @author xu shiheng
 */
@SuppressWarnings("rawtypes")
public class TreeDSGenerator {
    /**
     * 输出数据列表，作为treeBuilder的数据源
     */
    private List treeData = new LinkedList();

    public List getTreeData() {
        return treeData;
    }

    public void setTreeData(List treeData) {
        this.treeData = treeData;
    }

    /**
     * 输入数据源
     */
    private Collection<Object> treeSource;

    public Collection<Object> getTreeSource() {
        return treeSource;
    }

    public void setTreeSource(Collection<Object> treeSource) {
        this.treeSource = treeSource;
    }

    /**
     * 根据输入数据源创建 treeBuilder的数据源
     */
    @SuppressWarnings("unchecked")
    public void initTreeData() {
        try {

            for (Object obj : treeSource) {
                Class objClass = obj.getClass().getSuperclass();
                Method getId = objClass.getDeclaredMethod("getNodeId");
                Method getName = objClass.getDeclaredMethod("getNodeName");
                Method getNodeDescription = objClass.getDeclaredMethod("getNodeDescription");
                Method getParent = objClass.getDeclaredMethod("getNodeParentId");
                Method getUrl = objClass.getDeclaredMethod("getNodeUrl");

                String id = (String) getId.invoke(obj);
                String name = (String) getName.invoke(obj);
                String parentId = (String) getParent.invoke(obj);
                String description = (String) getNodeDescription.invoke(obj);
                String url = (String) getUrl.invoke(obj);

                Map nodeDataMap = new LinkedHashMap();
                nodeDataMap.put("id", id);
                nodeDataMap.put("name", name);
                if (parentId == null || parentId == "") {
                    parentId = "0";
                }
                nodeDataMap.put("parentId", parentId);
                nodeDataMap.put("description", description);
                nodeDataMap.put("url", url);
                treeData.add(nodeDataMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}