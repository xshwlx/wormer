package com.jd.uwp.accesscontrol.po;


import com.jd.uwp.core.po.TableBean;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 登陆用户
 * User: xushiheng
 * Date: 14-12-24
 * Time: 上午10:54
 */
public class Uwp_sys_user extends TableBean {

    public Uwp_sys_user() {
        this.clear();
    }

    public void clear() {
        this.sys_userID = 0;
        this.user_name = "";
        this.user_pwd = "";
        this.user_erp = "";
        this.user_email = "";
        this.last_login_time = "";
        this.last_login_ip = "";
        this.user_status = 0;
        this.ts = "";
        this.realName = "";

    }

    private int sys_userID;

    public void setSys_userID(int sys_userID) {
        this.sys_userID = sys_userID;
        conditionMap.put("sys_userID", sys_userID);
    }

    public int getSys_userID() {
        return this.sys_userID;
    }

    private String user_name;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        conditionMap.put("user_name", user_name);
    }

    public String getUser_name() {
        return this.user_name;
    }

    private String user_pwd;

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
        conditionMap.put("user_pwd", user_pwd);
    }

    public String getUser_pwd() {
        return this.user_pwd;
    }

    private String user_erp;

    public void setUser_erp(String user_erp) {
        this.user_erp = user_erp;
        conditionMap.put("user_erp", user_erp);
    }

    public String getUser_erp() {
        return this.user_erp;
    }

    private String user_email;

    public void setUser_email(String user_email) {
        this.user_email = user_email;
        conditionMap.put("user_email", user_email);
    }

    public String getUser_email() {
        return this.user_email;
    }

    private String last_login_time;

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
        conditionMap.put("last_login_time", last_login_time);
    }

    public String getLast_login_time() {
        return this.last_login_time;
    }

    private String last_login_ip;

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
        conditionMap.put("last_login_ip", last_login_ip);
    }

    public String getLast_login_ip() {
        return this.last_login_ip;
    }

    private int user_status;

    public void setUser_status(int user_status) {
        this.user_status = user_status;
        conditionMap.put("user_status", user_status);
    }

    public int getUser_status() {
        return this.user_status;
    }

    private String ts;

    public void setTs(String ts) {
        this.ts = ts;
        conditionMap.put("ts", ts);
    }

    public String getTs() {
        return this.ts;
    }

    private String realName;

    public void setRealName(String realName) {
        this.realName = realName;
        conditionMap.put("realName", realName);
    }

    public String getRealName() {
        return this.realName;
    }


    public boolean set(ResultSet rs) {
        try {
            this.sys_userID = rs.getInt("sys_userID");
            this.user_name = rs.getString("user_name");
            this.user_pwd = rs.getString("user_pwd");
            this.user_erp = rs.getString("user_erp");
            this.user_email = rs.getString("user_email");
            this.last_login_time = rs.getString("last_login_time");
            this.last_login_ip = rs.getString("last_login_ip");
            this.user_status = rs.getInt("user_status");
            this.ts = rs.getString("ts");
            this.realName = rs.getString("realName");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
