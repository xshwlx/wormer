package com.jd.uwp.webcontroller;

import com.jd.uwp.common.utils.OS;
import com.jd.uwp.core.ioc.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * 初始化类
 * User: xushiheng
 * Date: 14-12-5
 * Time: 上午9:31
 * To change this template use File | Settings | File Templates.
 */
public class UWPInit extends HttpServlet {
    Logger logger = Logger.getLogger(UWPInit.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        //UWP平台初始化
        //操作系统信息初始化
        logger.info("start init os info");
        OS.initOS();
        logger.info("init os success");
        logger.info("操作系统信息 " + OS.getOsDesc());
        logger.info("start init services and daos");
        ServiceFactory.doInit();
        logger.info("init services and daos success");
    }
}
