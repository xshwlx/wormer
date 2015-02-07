package com.jd.uwp.webcontroller;

import com.google.gson.Gson;
import com.jd.ci.action.UserActionRegister;
import com.jd.uwp.webcontroller.action.ActionConst;
import com.jd.uwp.webcontroller.action.IAction;
import com.jd.uwp.webcontroller.response.Response;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * action 执行器
 *
 * @author xushiheng
 */
public class ActionProcess {
    //servlet 属性注入区域
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String sessionid;
    private ServletContext servletContext;
    //参数注入区域
    private String actionName;
    private String jsonParams;

    public String process() {
        //具体action 类
        String actionClass = "";
        Response response = new Response();
        try {
            actionClass = FrameActionRegister.getAction(this.actionName);
            if (actionClass == null) {
                //优化为XML配置
                actionClass = UserActionRegister.getAction(this.actionName);
            }
            IAction action = (IAction) Class.forName(actionClass).newInstance();
            action.setRequest(this.getRequest());
            action.setResponse(this.getResponse());
            action.setServletContext(this.getServletContext());
            this.setSessionid(this.getSessionid());
            String result = action.execute(jsonParams);
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            response.setCode(ActionConst.RESPONSE_CODE_FAILED);
            response.setMessage(e.getMessage());
        }
        return new Gson().toJson(response);
    }


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public void setJsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
    }

    public String getSessionid() {
        return sessionid;
    }


    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
