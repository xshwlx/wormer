package com.jd.uwp.webcontroller.action;

import com.jd.uwp.accesscontrol.po.Uwp_sys_user;
import com.jd.uwp.webcontroller.session.Session;
import com.jd.uwp.webcontroller.session.SessionManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * action 抽象类
 * User: xushiheng
 * Date: 14-12-17
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAction implements IAction {

    public static String LOGIN_RESULT_NONEXIST = "'nonexist'"; //不存在
    public static String LOGIN_RESULT_TIMEOUT = "timeout"; //超时
    public static String LOGIN_RESULT_LOCKED = "locked";//账号锁定

    public Uwp_sys_user getUser() {
        return user;
    }

    public void setUser(Uwp_sys_user user) {
        this.user = user;
    }

    protected Uwp_sys_user user;   //当前登陆用户
    /**
     * 请求对象
     */
    protected HttpServletRequest request;
    /**
     * 响应对象
     */
    protected HttpServletResponse response;
    /**
     * 服务上下文
     */
    protected ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setSessionId(String sessionId) {
        if (sessionId != null) {
            if (sessionId.trim().length() > 0) {
                Session session = SessionManager.getManager().getSession(sessionId);
                this.user = (Uwp_sys_user) session.getUser();
            }
        }
    }

}
