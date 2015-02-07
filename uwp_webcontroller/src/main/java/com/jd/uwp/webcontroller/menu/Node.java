package com.jd.uwp.webcontroller.menu;

import com.google.gson.annotations.SerializedName;
import com.jd.uwp.webcontroller.action.PluginAction;

import java.io.Serializable;

public class Node implements Serializable, Comparable<Node>, Cloneable {

	private static final long serialVersionUID = -8670031862066922863L;

	private String id;

	private String name;

	private String englishName;

	private int displayOrder;

	private String note;

	private PluginAction[] pluginAction;

	private String moduleID;

	private String role;

    private String url;

    private Node[] nodes;

    private String modelPath;//模块路径

    private String module;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

	public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public PluginAction[] getPluginAction() {
		return pluginAction;
	}

	public void setPluginAction(PluginAction[] pluginAction) {
		this.pluginAction = pluginAction;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public int compareTo(Node item) {
		return this.displayOrder < item.displayOrder ? -1 : 1;
	}

	@Override
	public String toString() {
		return " id=" + id + " name=" + name + " moduleID=" + moduleID
				+ " displayOrder=" + displayOrder+ " url=" + url;
	}

	public Node clone() throws CloneNotSupportedException {
		Node node = (Node) super.clone();
		if (this.pluginAction != null) {
			PluginAction[] pluginAction1 = new PluginAction[this.pluginAction.length];
			for (int i = 0; i < this.pluginAction.length; i++) {
				pluginAction1[i] = (PluginAction) this.pluginAction[i];
			}
			node.pluginAction = pluginAction1;
		}
		return node;
	}

}
