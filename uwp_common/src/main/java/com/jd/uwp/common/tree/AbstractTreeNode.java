package com.jd.uwp.common.tree;

import java.util.List;

/**
 * 树节点类
 *
 * @author xu shiheng
 */
public abstract class AbstractTreeNode {
    /**
     * 节点编号
     */
    protected String id;
    /**
     * 节点内容
     */
    protected String name;
    /**
     * 节点描述
     */
    protected String description;
    /**
     * 父节点编号
     */
    protected String parentId;
    /**
     * 节点权值
     */
    protected int position;
    /**
     * 节点图标
     */
    protected int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    protected String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * 是否可见，默认为true
     */
    protected boolean visible = true;
    /**
     * 父节点引用
     */
    protected AbstractTreeNode parentNode;
    /**
     * 孩子节点列表
     */
    protected ChildrenNode children = new ChildrenNode();

    // 添加子节点
    public void addChild(AbstractTreeNode node) {
        this.children.addChild(node);
    }

    // 子节点横向排序
    public void sortChildren() {
        if (children != null && children.getSize() != 0) {
            children.sortChildren();
        }
    }

    // 先序遍历，构造功能叶子列表
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void initializeLeafList(List leafList) {
        if (children == null || children.getSize() == 0) {
            leafList.add(this);
        } else {
            children.initializeLeafList(leafList);
        }
    }

    // 先序遍历，设置该节点下的所有功能路径为不可见
    public void setTreeNotVisible() {
        visible = false;
        if (children != null && children.getSize() != 0) {
            children.setTreeNotVisible();
        }
    }

    // 先序遍历，设置该节点下的所有功能路径为可见
    public void setTreeVisible() {
        visible = true;
        if (children != null && children.getSize() != 0) {
            children.setTreeVisible();
        }
    }

    // 设置包含该叶子节点的功能路径可见
    public void setRouteVisible() {
        visible = true;
        for (AbstractTreeNode parentNode = this.parentNode; parentNode != null; parentNode = parentNode.parentNode) {
            parentNode.visible = true;
        }
    }

    // 对包含该叶子节点的功能路径权值加1
    public void increaseRouteWeight() {
        position++;
        updateNodeWeightToDB(this);
        for (AbstractTreeNode parentNode = this.parentNode; parentNode != null; parentNode = parentNode.parentNode) {
            parentNode.position++;
            updateNodeWeightToDB(parentNode);
        }
    }

    // 更新节点权值到数据库
    public void updateNodeWeightToDB(AbstractTreeNode node) {
        // 暂时不实现，实际应用中需要实现该方法
        // 或者用户退出系统时，遍历整棵树，统一更新所有节点的权值到数据库中，应该这样做比较好，一次性统一处理
    }

    // 先序遍历，搜索菜单节点，同时进行功能路径过滤
    public void searchTreeNode(String keyWord) {
        if (this.name.indexOf(keyWord) > -1) {
            this.setTreeVisible();
            this.setRouteVisible();
        } else {
            if (children != null && children.getSize() != 0) {
                children.searchTreeNode(keyWord);
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public AbstractTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(AbstractTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public ChildrenNode getChildren() {
        return children;
    }

    public void setChildren(ChildrenNode children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
