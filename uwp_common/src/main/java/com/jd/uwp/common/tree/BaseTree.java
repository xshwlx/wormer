package com.jd.uwp.common.tree;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-16
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseTree {

    protected String nodeId;
    protected String nodeName;
    protected String nodeParentId;
    protected String nodeDescription;
    protected String nodeUrl;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeParentId() {
        return nodeParentId;
    }

    public void setNodeParentId(String nodeParentId) {
        this.nodeParentId = nodeParentId;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }
}
