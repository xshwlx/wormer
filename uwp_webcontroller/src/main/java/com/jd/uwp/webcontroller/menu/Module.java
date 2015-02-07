package com.jd.uwp.webcontroller.menu;

import java.io.Serializable;

/**
 *
 */
public class Module implements Serializable, Comparable<Module>, Cloneable {

	private static final long serialVersionUID = -8456653089437819308L;

	private String id;

	private String name;

	private String englishName;

	private int displayOrder;

    private String module;

	private String url;

	private Node[] nodes;

	private String role;

    private String icon;

    private String modelPath;//模块路径

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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	   this.url = url;
	}
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }


    public int compareTo(Module module) {
		return this.displayOrder < module.displayOrder ? -1 : 1;
	}

	@Override
	public String toString() {
		return " id=" + id + " name=" + name + " englishName="
				+ englishName + " displayOrder=" + displayOrder + " url=" + url;
	}

	public Module clone() throws CloneNotSupportedException {
		Module module = (Module) super.clone();
		if (nodes !=null) {
			Node[] nodes1 = new Node[this.nodes.length];
			for (int i = 0; i < nodes.length; i++) {
				nodes1[i] = (Node) this.nodes[i].clone();
			}
			module.nodes = nodes1;
		}
		return module;
	}

}
