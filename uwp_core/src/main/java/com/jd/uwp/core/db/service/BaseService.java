package com.jd.uwp.core.db.service;

import com.jd.uwp.core.db.mysql.dao.BaseDao;
import com.jd.uwp.core.po.TableBean;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 基础服务
 *
 * @author :    xushiheng
 * @version :
 * @date :
 * @since :
 */
public interface BaseService {

    public void setIsCloseAfterUsed(boolean isCloseAfterUsed);

    public boolean getIsCloseAfterUsed();

    public void setConn(Connection conn, boolean isCloseAfterUsed);

    public void setConn(Connection conn, boolean isCloseAfterUsed, boolean isCommitAfterUsed);

    public Connection getConn();

    public void setConn(Connection conn);

    public void closeConnection();

    public void begin(BaseDao dao);

    public void end(BaseDao dao);

    public void rollback(BaseDao dao);

    public void commit(BaseDao dao);

    /**
     * 添加对象
     *
     * @param
     * @param table
     * @param id
     * @return
     */
    public int addObject(Map data, String table, String id);

    /**
     * @param obj
     * @return Inserted rows Id
     */
    public int addObject(Object obj);

    /**
     * 修改对象
     *
     * @param data
     * @param table
     * @param id
     */
    public int updateObject(Map data, String table, String id);

    public int updateObject(Object obj);

    /**
     * 修改对象
     *
     * @param data
     * @param table
     * @param where
     */
    public int updateObject(Map data, String table, Map where);

    /**
     * 查询对象集合
     *
     * @param data
     * @param oclass
     * @return
     * @throws Exception
     */
    public List<?> queryObjectList(Map data, Class oclass);

    public List<?> queryAllObject(Class oclass);

    public List<?> queryObjectList(Map data, Class oclass, String orderBy);

    /**
     * 查询对象
     *
     * @param
     * @param oclass
     * @return
     * @throws Exception
     */
    public Object queryObject(TableBean oclass);

    public Object queryObject(TableBean oclass, String sql);

    public Object queryObject(Map data, Class oclass);

    /**
     * 删除的公用
     *
     * @param data
     * @param table
     */
    public int deleteObject(Map data, String table);

    public int deleteObject(Map data, Class oclass);

    public int deleteObject(Object obj);


}