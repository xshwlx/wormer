package com.jd.uwp.webcontroller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WEB访问控制器
 *
 * @author xushiheng
 * @version 1.0
 */
@WebServlet("/mainController")
public class MainController extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public MainController() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //参数获取
        String result = null;
        request.setCharacterEncoding("utf-8");
        String actionName = (String) request.getParameter("action");
        String sessionid = (String) request.getParameter("sessionid");
        String jsonParams = (String) request.getParameter("jsonParam");
        //请求转发
        ActionProcess process = new ActionProcess();
        process.setSessionid(sessionid);
        process.setRequest(request);
        process.setResponse(response);
        process.setActionName(actionName);
        process.setJsonParams(jsonParams);
        process.setSessionid(sessionid);
        process.setServletContext(this.getServletContext());
        result = process.process();
        //登出 session 控制
        //数据返回
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);
        response.flushBuffer();
    }

}
