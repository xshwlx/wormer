package com.jd.uwp.webcontroller.action;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * action 接口
 * 后台所有动作类必须实现
 */
public interface IAction {
    /**
     * 执行 action
     *
     * @param json
     */
    public String execute(String json) throws Exception;

    /**
     * servlet context 设置
     *
     * @param servletContext
     */
    public void setServletContext(ServletContext servletContext);

    /**
     * response 设置
     *
     * @param response
     */
    public void setResponse(HttpServletResponse response);

    /**
     * request 设置
     *
     * @param request
     */
    public void setRequest(HttpServletRequest request);

    /**
     * 设置 sessionID
     *
     * @param sessionId
     */
    public void setSessionId(String sessionId);
}
