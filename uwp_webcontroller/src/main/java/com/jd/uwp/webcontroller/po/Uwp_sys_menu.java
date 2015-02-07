package com.jd.uwp.webcontroller.po;


import com.jd.uwp.core.po.TableBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Uwp_sys_menu extends TableBean {

    public Uwp_sys_menu() {
        this.clear();
    }

    public void clear() {
        this.sys_menuID = 0;
        this.menu_model = "";
        this.menu_name = "";
        this.parent_menuID = 0;
        this.icon = "";
        this.menu_code = "";
        this.description = "";
        this.menu_status = "";
        this.menu_order = 0;
        this.ts = "";

    }

    private int sys_menuID;

    public void setSys_menuID(int sys_menuID) {
        this.sys_menuID = sys_menuID;
        conditionMap.put("sys_menuID", sys_menuID);
    }

    public int getSys_menuID() {
        return this.sys_menuID;
    }

    private String menu_model;

    public void setMenu_model(String menu_model) {
        this.menu_model = menu_model;
        conditionMap.put("menu_model", menu_model);
    }

    public String getMenu_model() {
        return this.menu_model;
    }

    private String menu_name;

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
        conditionMap.put("menu_name", menu_name);
    }

    public String getMenu_name() {
        return this.menu_name;
    }

    private int parent_menuID;

    public void setParent_menuID(int parent_menuID) {
        this.parent_menuID = parent_menuID;
        conditionMap.put("parent_menuID", parent_menuID);
    }

    public int getParent_menuID() {
        return this.parent_menuID;
    }

    private String icon;

    public void setIcon(String icon) {
        this.icon = icon;
        conditionMap.put("icon", icon);
    }

    public String getIcon() {
        return this.icon;
    }

    private String menu_code;

    public void setMenu_code(String menu_code) {
        this.menu_code = menu_code;
        conditionMap.put("menu_code", menu_code);
    }

    public String getMenu_code() {
        return this.menu_code;
    }

    private String description;

    public void setDescription(String description) {
        this.description = description;
        conditionMap.put("description", description);
    }

    public String getDescription() {
        return this.description;
    }

    private String menu_status;

    public void setMenu_status(String menu_status) {
        this.menu_status = menu_status;
        conditionMap.put("menu_status", menu_status);
    }

    public String getMenu_status() {
        return this.menu_status;
    }

    private int menu_order;

    public void setMenu_order(int menu_order) {
        this.menu_order = menu_order;
        conditionMap.put("menu_order", menu_order);
    }

    public int getMenu_order() {
        return this.menu_order;
    }

    private String ts;

    public void setTs(String ts) {
        this.ts = ts;
        conditionMap.put("ts", ts);
    }

    public String getTs() {
        return this.ts;
    }


    public boolean set(ResultSet rs) {
        try {
            this.sys_menuID = rs.getInt("sys_menuID");
            this.menu_model = rs.getString("menu_model");
            this.menu_name = rs.getString("menu_name");
            this.parent_menuID = rs.getInt("parent_menuID");
            this.icon = rs.getString("icon");
            this.menu_code = rs.getString("menu_code");
            this.description = rs.getString("description");
            this.menu_status = rs.getString("menu_status");
            this.menu_order = rs.getInt("menu_order");
            this.ts = rs.getString("ts");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
