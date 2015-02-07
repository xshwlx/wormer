package com.jd.uwp.core.test.om;

import com.jd.uwp.common.utils.OS;
import com.jd.uwp.common.utils.WebApp;
import com.jd.uwp.core.db.service.BaseService;
import com.jd.uwp.core.dbconnction.DBConnection;
import com.jd.uwp.core.ioc.ServiceFactory;
import com.jd.uwp.core.util.PoCreater;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-24
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class TestQueryObject {

    public static void main(String[] args) {
        String appName = "ci_layout";
        WebApp.setWeb(false);
        OS.initOS();
        ServiceFactory.doInit(appName);

        Connection connectionMan = null;
        PoCreater rdb = new PoCreater();
        connectionMan = DBConnection.getConn();

        BaseService baseService = (BaseService) ServiceFactory.getModules("baseService");
        //验证数据库用户名 密码
//      Uwp_sys_user user = new Uwp_sys_user();
//      user.setUser_name("1");
//      user.setUser_pwd("1");
//      Uwp_sys_user queryUser = (Uwp_sys_user) baseService.queryObject(user);

    }
}
