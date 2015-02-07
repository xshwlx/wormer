package com.jd.uwp.core.ioc;

import com.jd.uwp.common.utils.WebApp;
import com.jd.uwp.common.utils.SysParameters;
import com.jd.uwp.core.db.service.BaseService;
import com.jd.uwp.core.dbconnction.DBConnection;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author :    xushiheng
 * @version :
 * @date :
 * @since :
 */
public class ServiceFactory {
    private static Logger logger = Logger.getLogger(ServiceFactory.class);

    public static HashMap<String, Object> services = new HashMap();
    public static HashMap<String, ArrayList<String>> serviceDaos = new HashMap();

    public static Object getModules(String name) {
        return getModulesBase(name, true);
    }

    public static Object getModulesBase(String name, boolean closeCon) {
        ArrayList<?> fieldList = (ArrayList<?>) serviceDaos.get(name);
        Object object = services.get(name);
        Class<? extends Object> cObject = object.getClass();
        Object newObject = null;
        try {
            Class<?> newClass = Class.forName(cObject.getName());
            newObject = newClass.newInstance();
            BeanUtils.copyProperties(newObject, object);
            for (int i = 0; i < fieldList.size(); i++) {
                String fieldName = fieldList.get(i).toString();
                try {
                    fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
                    Method getMethod = cObject.getMethod("get" + fieldName);
                    Object fieldObj = getMethod.invoke(object);
                    Method setMethod = newClass.getMethod("set" + fieldName, fieldObj.getClass().getInterfaces());
                    setMethod.invoke(newObject, fieldObj.getClass().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((BaseService) newObject).setConn(DBConnection.getConn(), closeCon);
        return newObject;
    }

    /**
     * @ throws
     */
    public static void parseXML(String xmlpath, String databasetype) {
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(xmlpath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("service"); i.hasNext(); ) {
            try {
                Element foo = (Element) i.next();
                String name = foo.attributeValue("name");
                String servicepath = foo.attributeValue("class");
//	    				String daopath=foo.attributeValue("daoclass");
                ArrayList<String> daoImplPaths = new ArrayList<String>();
                ArrayList<String> daoMethodName = new ArrayList<String>();
                for (Iterator j = foo.elementIterator("dao"); j.hasNext(); ) {
                    Element a = (Element) j.next();
                    String daoname = a.attributeValue("name");
                    String daopackagepath = a.attributeValue("class");
                    String daoimplpath = daopackagepath.replace("%database%", databasetype);
                    daoImplPaths.add(daoimplpath);
                    daoMethodName.add(daoname);
                }
                serviceDaos.put(name, daoMethodName);
                Object o = Into(daoImplPaths, servicepath, daoMethodName);
                services.put(name, o);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


    public static void doInit() {
        try {
            DBConnection.setConfigure();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            InitServices();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }

    /**
     * 初始化数据库链接
     * 初始化服务
     *
     * @param appName web 应用名称
     */
    public static void doInit(String appName) {
        try {
            DBConnection.setConfigure(appName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            InitServices(appName);
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }

    public static void InitServices() {
        String cmdFile = WebApp.getClassPath() + SysParameters.separator + "services.xml";
        if (new File(cmdFile).exists()) {
            parseXML(cmdFile, DBConnection.DATABASE_TYPE);
        } else {
            cmdFile = WebApp.getConfigPath() + SysParameters.separator + "services.xml";
            parseXML(cmdFile, DBConnection.DATABASE_TYPE);
        }

    }

    public static void InitServices(String appName) {
        String cmdFile = WebApp.getPlatformPatn() + "/" + appName + "/target/classes" + SysParameters.separator + "services.xml";
        if (new File(cmdFile).exists()) {
            parseXML(cmdFile, DBConnection.DATABASE_TYPE);
        } else {
            cmdFile = WebApp.getPlatformPatn() + "/" + appName + "/WEB-INF/classes" + SysParameters.separator + "services.xml";
            parseXML(cmdFile, DBConnection.DATABASE_TYPE);
        }

    }

    /**
     * @ throws
     */
    public static Object Into(ArrayList<String> daoImpl, String serviceImpl, ArrayList<String> Field) {
        Object bService = null;
        try {
            bService = Class.forName(serviceImpl).newInstance();
            Class a = Class.forName(serviceImpl);
            Field[] fields = a.getDeclaredFields();//
            for (int k = 0; k < daoImpl.size(); k++) {
                Object basedao = Class.forName(daoImpl.get(k)).newInstance();
                for (int i = 0; i < fields.length; i++) {
                    String tempField = fields[i].getName();
                    if (tempField.equals(Field.get(k))) {
                        String tempMethod = "set"
                                + tempField.substring(0, 1).toUpperCase()
                                + tempField.substring(1);
                        Method method = a.getMethod(tempMethod, basedao.getClass().getInterfaces());
                        method.invoke(bService, basedao);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bService;
    }
}
 
