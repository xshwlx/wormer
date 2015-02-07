package com.jd.uwp.core.db.service;


import com.jd.uwp.common.utils.Bean2Map;
import com.jd.uwp.core.db.mysql.dao.BaseDao;
import com.jd.uwp.core.db.mysql.dao.BaseDaoImpl;
import com.jd.uwp.core.dbconnction.DBConnection;
import com.jd.uwp.core.orm.IfORM;
import com.jd.uwp.core.po.TableBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl implements BaseService {


    private boolean isCommitAfterUsed = true;

    public void setIsCommitAfterUsed(boolean isCommitAfterUsed) {
        this.isCommitAfterUsed = isCommitAfterUsed;
    }

    public boolean getIsCommitAfterUsed() {
        return this.isCommitAfterUsed;
    }


    private boolean isCloseAfterUsed = true;

    public void setIsCloseAfterUsed(boolean isCloseAfterUsed) {
        this.isCloseAfterUsed = isCloseAfterUsed;
    }

    public boolean getIsCloseAfterUsed() {
        return this.isCloseAfterUsed;
    }

    public BaseServiceImpl() {
        baseDao = new BaseDaoImpl();
    }

    protected BaseDao baseDao;

    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    private Connection conn = null;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
        this.isCloseAfterUsed = true;
    }

    public void setConn(Connection conn, boolean isCloseAfterUsed) {
        this.conn = conn;
        this.isCloseAfterUsed = isCloseAfterUsed;
    }

    public void setConn(Connection conn, boolean isCloseAfterUsed, boolean isCommitAfterUsed) {
        this.conn = conn;
        this.isCloseAfterUsed = isCloseAfterUsed;
        this.isCommitAfterUsed = isCommitAfterUsed;
    }

    public void closeConnection() {
        DBConnection.closeConnection(conn);
    }

    public void begin(BaseDao dao) {
//		baseDao.setDaoName( dao.getClass( ).getName( ) );
        dao.setDaoName(dao.getClass().getName());
        dao.setConn(conn, isCloseAfterUsed, isCommitAfterUsed);
        baseDao.setConn(conn, isCloseAfterUsed, isCommitAfterUsed);
    }

    public void end(BaseDao dao) {
        dao.Close();
    }

    public void rollback(BaseDao dao) {
        dao.rollback();
    }

    public void commit(BaseDao dao) {
        dao.commit();
    }

    public int addObject(Map data, String table, String id) {
        int o = 0;
        this.begin(baseDao);
        try {
            o = baseDao.addObject(data, table, id);
        } catch (SQLException e) {
            e.printStackTrace();
            this.rollback(baseDao);
        }
        this.end(baseDao);
        return o;
    }

    /* (non-Javadoc)
     * @see com.common.core.module.BaseService#addObject(java.lang.Object)
     */
    public int addObject(Object bean) {
        try {

            Map data = (HashMap) Bean2Map.convertBean(bean);

            String table = bean.getClass().getSimpleName().toLowerCase();
//			String id = "pk_"+table.toLowerCase(); // change the tbale ID
            String id = table.toLowerCase() + "ID";
            data.remove(id);
            return addObject(data, table, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int updateObject(Map data, String table, String id) {
        int code = 0;
        this.begin(baseDao);
        try {
            code = baseDao.updateObject(data, table, id);
        } catch (SQLException e) {
            e.printStackTrace();
            this.rollback(baseDao);
        }
        this.end(baseDao);
        return code;
    }

    public int updateObject(Object bean) {
        int code = 0;
        try {
            Map data = (HashMap) Bean2Map.convertBean(bean);
            String table = bean.getClass().getSimpleName().toLowerCase();
//			String id = "pk_"+table.toLowerCase();
            String id = table.toLowerCase() + "ID";
            code = updateObject(data, table, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public int updateObject(Map data, String table, Map where) {
        int code = 0;
        this.begin(baseDao);
        try {
            code = baseDao.updateObject(data, table, where);
        } catch (SQLException e) {
            e.printStackTrace();
            this.rollback(baseDao);
        }
        this.end(baseDao);
        return code;
    }

    public List<Object> queryObjectList(Map data, Class oclass) {
        List<Object> list = null;
        this.begin(baseDao);
        try {
            list = baseDao.queryObjectList(data, oclass);
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return list;
    }

    public List<Object> queryObjectList(Map data, Class oclass, String orderby) {
        List<Object> list = null;
        this.begin(baseDao);
        try {
            list = baseDao.queryObjectList(data, oclass, orderby);
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return list;
    }

    @Override
    public Object queryObject(Map data, Class oclass) {
        Object o = new Object();
        this.begin(baseDao);
        try {
            o = baseDao.queryObject(data, oclass);
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return o;
    }


    public Object queryObject(IfORM obj) {
        Object o = new Object();
        this.begin(baseDao);
        try {
            o = baseDao.queryObject(obj.getDataMap(), obj.getClass());
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return o;
    }

    public int deleteObject(Map data, String table) {
        int code = 0;
        this.begin(baseDao);
        try {
            code = baseDao.deleteObject(data, table);
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return code;
    }

    public int deleteObject(Map data, Class oclass) {
        int code = 0;
        this.begin(baseDao);
        try {
            code = baseDao.deleteObject(data, oclass.getSimpleName().toLowerCase());
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return code;
    }

    public int deleteObject(Object bean) {
        int code = 0;
        this.begin(baseDao);
        try {
            HashMap data = (HashMap) Bean2Map.convertBean(bean);
            String table = bean.getClass().getSimpleName().toLowerCase();
//			String id = "pk_"+table.toLowerCase();
            String id = table.toLowerCase() + "ID";
            code = baseDao.deleteObject(data, table, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.end(baseDao);
        return code;
    }

    @Override
    public List<Object> queryAllObject(Class oclass) {
        List<Object> list = null;
        this.begin(baseDao);
        try {
            list = baseDao.queryAllObject(oclass);
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return list;
    }

    @Override
    public Object queryObject(TableBean oclass) {
        Object o = new Object();
        this.begin(baseDao);
        try {
            o = baseDao.queryObject(oclass.getConditionMap(), oclass.getClass());
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return o;
    }

    @Override
    public Object queryObject(TableBean oclass, String sql) {
        Object o = new Object();
        this.begin(baseDao);
        try {
            // baseDao.queryObject();
        } catch (Exception e) {
            this.rollback(baseDao);
            e.printStackTrace();
        }
        this.end(baseDao);
        return o;
    }

}
