package com.jd.uwp.core.util;

import com.jd.uwp.common.utils.OS;
import com.jd.uwp.common.utils.WebApp;
import com.jd.uwp.core.dbconnction.DBConnection;
import com.jd.uwp.core.ioc.ServiceFactory;

import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据库表映射为持久化 PO
 *
 * @author : xushiheng
 * @date: 14-12-22
 * Time: 下午3:58
 */
public class Table2Po {

    public static void main(String[] args) {

        try {
            String appName = "ci_layout";
            //源码包路径
            String sourcePath = "/uwp_webcontroller/src/main/java";
            //包名
            String packetPath = "com.jd.uwp.webcontroller.po";
            //数据库表名称
            String[] tables = {"uwp_sys_menu"};
            Table2Po.createPO(appName, sourcePath, tables, packetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 持久对象po 创建
     *
     * @param sourcePath
     * @param tables
     * @param packetPath
     * @throws IOException
     */
    public static String baseTable = "com.jd.uwp.core.po.TableBean";

    public static void createPO(String sourcePath, String[] tables, String packetPath) throws IOException {

        WebApp.setWeb(true);
        OS.initOS();
        ServiceFactory.doInit();

        Connection connectionMan = null;
        PoCreater rdb = new PoCreater();
        connectionMan = DBConnection.getConn();
        List importClasses = new LinkedList<String>();
        importClasses.add(baseTable);
        for (String table : tables) {
            connectionMan = DBConnection.getConn();
            rdb.ReadDBToJava(connectionMan, table, sourcePath, packetPath, importClasses);
        }
    }

    public static void createPO(String appName, String sourcePath, String[] tables, String packetPath) throws IOException {

        WebApp.setWeb(false);
        OS.initOS();
        ServiceFactory.doInit(appName);

        Connection connectionMan = null;
        PoCreater rdb = new PoCreater();
        connectionMan = DBConnection.getConn();
        List importClasses = new LinkedList<String>();
        importClasses.add(baseTable);
        for (String table : tables) {
            connectionMan = DBConnection.getConn();
            rdb.ReadDBToJava(connectionMan, table, sourcePath, packetPath, importClasses);
        }
    }

}
