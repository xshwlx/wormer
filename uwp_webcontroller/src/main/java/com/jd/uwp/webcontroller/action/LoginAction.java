package com.jd.uwp.webcontroller.action;

import com.google.gson.Gson;
import com.jd.uwp.accesscontrol.po.Uwp_sys_user;
import com.jd.uwp.accesscontrol.vo.LoginUser;
import com.jd.uwp.common.constans.UWPConstans;
import com.jd.uwp.core.db.service.BaseService;
import com.jd.uwp.core.ioc.ServiceFactory;
import com.jd.uwp.webcontroller.response.Response;
import com.jd.uwp.webcontroller.session.Session;
import com.jd.uwp.webcontroller.session.SessionManager;

/**
 * 登陆控制类
 * User: xushiheng
 * Date: 14-12-17
 * Time: 上午10:46
 */
public class LoginAction extends AbstractAction {

    @Override
    public String execute(String json) {
        Response response = new Response();
        try {
            Gson gson = new Gson();
            LoginUser obj = gson.fromJson(json, LoginUser.class);
            String userName = obj.getUser_code();
            String pwd = obj.getUser_pwd();
            BaseService baseService = (BaseService) ServiceFactory.getModules("baseService");
            //验证数据库用户名 密码
            Uwp_sys_user user = new Uwp_sys_user();
            user.setUser_name(userName);
            user.setUser_pwd(pwd);
            Uwp_sys_user queryUser = (Uwp_sys_user) baseService.queryObject(user);
            int result = ActionConst.LOGIN_RESULT_FAIL;
            if (queryUser != null) {
                if (queryUser.getUser_status() == UWPConstans.USER_STATUS_STOP) {
                    response.setMessage(LOGIN_RESULT_LOCKED);
                }
                result = ActionConst.LOGIN_RESULT_SUCCESS;
            }
            if (result == ActionConst.LOGIN_RESULT_FAIL) {
                response.setCode(ActionConst.RESPONSE_CODE_FAILED);
                response.setMessage(LOGIN_RESULT_NONEXIST);
            } else {
                String sessionid = "user_" + System.nanoTime();
                Session session = new Session();
                session.setSessionId(sessionid);
                session.setUser(queryUser);
                response.setData(session);
                SessionManager.getManager().newCheckedClient(sessionid, session);
                this.getRequest().getSession().setAttribute("sessionObj", session);
                response.setCode(ActionConst.RESPONSE_CODE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ActionConst.RESPONSE_CODE_PART_FAILED);
            response.setMessage(e.getMessage());
        }
        return new Gson().toJson(response);
    }
}
