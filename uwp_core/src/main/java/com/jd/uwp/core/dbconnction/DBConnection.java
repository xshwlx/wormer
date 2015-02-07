package com.jd.uwp.core.dbconnction;

import com.jd.uwp.common.utils.SysParameters;
import com.jd.uwp.common.utils.WebApp;
import com.jd.uwp.core.db.service.IdService;
import com.jd.uwp.core.ioc.ServiceFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author :    xushiheng
 * @version :
 * @date :
 * @since :
 */
public class DBConnection {
    public static String DATABASE_TYPE;
    public static HashMap<String, String> dataBaseSettting = new HashMap<String, String>();
    public static String defaultConn;
    private static Logger logger = Logger.getLogger(DBConnection.class);

    /**
     * 解析 proxool 配置文件
     *
     * @param path
     */
    public static void proxoolXML(String path) {
        logger.info("Load proxool config file from : " + path);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Element el = root.element("dbs");
        el.elementIterator();
        for (Iterator<?> i = el.elementIterator(); i.hasNext(); ) {
            Element e = (Element) i.next();
            DBConnection.dataBaseSettting.put(e.getName(), e.getText());
        }
    }

    /**
     * 设置数据库连接池配置
     */
    public static void setConfigure() {
        Connection conn = null;
        try {
            String path = "";
            if (WebApp.isWebProject()) {
                path = WebApp.getRootPath() + "/WEB-INF/classes/xml" + SysParameters.separator + "proxool.xml";
            } else {
                path = WebApp.getRootPath() + "/bin/xml/proxool.xml";
            }
            if (new File(path).exists()) {
                proxoolXML(path);
            } else {
                path = WebApp.getConfigPath() + SysParameters.separator + "proxool.xml";
                proxoolXML(path);
            }
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            JAXPConfigurator.configure(path, false);

            defaultConn = dataBaseSettting.get("masterDBS");
            conn = getConn();
            DATABASE_TYPE = conn.getMetaData().getDatabaseProductName().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection(conn);
                conn = null;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 设置数据库连接池配置
     */
    public static void setConfigure(String appName) {
        Connection conn = null;
        try {
            String path = "";
            path = WebApp.getPlatformPatn() + "/" + appName + "/WEB-INF/classes" + SysParameters.separator + "proxool.xml";
            if (new File(path).exists()) {
                proxoolXML(path);
            } else {
                path = WebApp.getPlatformPatn() + "/" + appName + "/target/classes" + SysParameters.separator + "proxool.xml";
                proxoolXML(path);
            }
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            JAXPConfigurator.configure(path, false);
            defaultConn = dataBaseSettting.get("masterDBS");
            conn = getConn();
            DATABASE_TYPE = conn.getMetaData().getDatabaseProductName().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection(conn);
                conn = null;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取链接
     * getConn
     *
     * @return
     */
    public static Connection getConn() {

        int error = 0;
        Connection connection = null;
        while (connection == null) {
            try {
                connection = DriverManager.getConnection(defaultConn);
            } catch (Exception e) {
                error++;
                int delay = Integer.parseInt(dataBaseSettting.get("retryDelay"));
                try {
                    Thread.sleep(delay);
                    if (connection != null) {
                        closeConnection(connection);
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                if (error >= Integer.parseInt(dataBaseSettting.get("retryNumber"))) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        if (connection == null) {
            System.out.println("get connection = null");
        } else {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 获取链接
     * getConn
     *
     * @param dbname
     * @return
     */
    public static Connection getConn(String dbname) {
        int error = 0;
        Connection connection = null;
        while (connection == null) {
            try {
                connection = DriverManager.getConnection(dbname);
            } catch (Exception e) {
                error++;
                e.printStackTrace();
                if (error >= Integer.parseInt(dataBaseSettting.get("retryNumber"))) {
                    error = 0;
                    break;
                }
            }
        }
        return connection;
    }

    /**
     * 输出连接池链接信息
     *
     * @param alias 数据库链接别名
     * @return
     */
    public static String showInfo(String alias) {
        int maxConn = 0;
        int activeConn = 0;
        int availableConn = 0;
        long sum = 0;
        String date = "";
        try {
            SnapshotIF snap = ProxoolFacade.getSnapshot(alias, true);
            maxConn = snap.getMaximumConnectionCount();
            activeConn = snap.getActiveConnectionCount();
            availableConn = snap.getAvailableConnectionCount();
            sum = snap.getConnectionCount();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = formater.format(new Date());

        } catch (ProxoolException e) {
        }
        return "max:" + maxConn + "\t sum" + sum + "\t active:" + activeConn + "\t available:" + availableConn + "\t date:" + date;
    }


    /**
     * 关闭数据库链接操作
     * closeConnection
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.commit();
                    conn.close();
                    conn = null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /*
     */
    private static String KEY = "id_sync_key";

    /**
     * 获取表数据id
     * getNextSqlID
     *
     * @param tableName
     * @param idName
     * @return
     */
    public static int getNextSqlID(String tableName, String idName) {
        Connection tmpConn = null;
        int nextID = 0;
        try {
            synchronized (KEY) {
                tmpConn = getConn();
                nextID = getNextSqlID(tmpConn, tableName, idName);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (tmpConn != null) {
                try {
                    tmpConn.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                tmpConn = null;
            }
        }
        return nextID;
    }

    /**
     * getNextSqlID 代码移植性 数据库移植
     *
     * @param conn
     * @param tableName
     * @param idName
     * @return
     */
    private static int getNextSqlID(Connection conn, String tableName, String idName) {
        int nextID = 0;
        try {
            IdService idService = (IdService) ServiceFactory.getModules("idService");
            idService.setConn(conn, false);
            nextID = idService.getNextId(tableName, idName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return nextID;
    }

}
