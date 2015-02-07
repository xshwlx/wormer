package com.jd.uwp.accesscontrol.vo;

/**
 * 登陆用户
 *
 * @author xushiheng
 * @date 2014/12/23
 */
public class LoginUser {
    //登陆用户名
    private String user_code;
    //登陆密码
    private String user_pwd;
    //记录 sessionID
    private String sessionid;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

}
