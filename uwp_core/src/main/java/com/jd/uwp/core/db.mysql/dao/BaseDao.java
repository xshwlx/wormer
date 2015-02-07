package com.jd.uwp.core.db.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库操作接口
 *
 * @author xushiheng
 */
public interface BaseDao {
    /**
     * 设置DAO名称
     *
     * @param daoName
     */
    public void setDaoName(String daoName);

    /**
     * 获取DAO 名称
     *
     * @return
     */
    public String getDaoName();

    /**
     * 获取数据库链接
     *
     * @return
     */
    public Connection getConn();

    /**
     * 数据库链接设置
     *
     * @param conn
     */
    public void setConn(Connection conn);

    /**
     * 数据库链接设置
     *
     * @param conn   数据库链接
     * @param closed 一次操作后是否关闭链接
     */
    public void setConn(Connection conn, boolean closed);

    /**
     * @param conn
     * @param isCloseAfterUsed
     * @param isCommitAfterUsed
     */
    public void setConn(Connection conn, boolean isCloseAfterUsed, boolean isCommitAfterUsed);

    /**
     * 链接关闭
     */
    public void Close();

    /**
     * @throws SQLException
     */
    public void unlock() throws SQLException;

    /**
     * 提交操作
     *
     * @param connection
     * @return
     */
    public boolean commit(Connection connection);

    /**
     * 提交操作
     *
     * @return
     */
    public boolean commit();

    /**
     * 回滚操作
     *
     * @param connection
     * @return
     */
    public boolean rollback(Connection connection);

    /**
     * 回滚操作
     *
     * @return
     */
    public boolean rollback();

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPrepareStatement(String sql) throws SQLException;

    /**
     * @param sql
     * @param isSync
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPrepareStatement(String sql, boolean isSync) throws SQLException;

    /**
     * 获取结果集
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getResultSet() throws SQLException;

    /**
     * 获取sql执行结果集
     *
     * @param pres
     * @return
     * @throws SQLException
     */
    public ResultSet getResultSet(PreparedStatement pres) throws SQLException;

    /**
     * 更新对象
     *
     * @param data  key 字段名 value 数据
     * @param table 表名称
     * @param id    表ID
     * @return 更新成功或者失败
     * @throws SQLException
     */
    public int updateObject(Map data, String table, String id) throws SQLException;

    /**
     * 更新对象
     *
     * @param data
     * @param table
     * @param where
     * @return
     * @throws SQLException
     */
    public int updateObject(Map data, String table, Map where) throws SQLException;

    /**
     * 添加对象
     *
     * @param data
     * @param table
     * @param id
     * @return
     * @throws SQLException
     */
    public int addObject(Map data, String table, String id) throws SQLException;

    /**
     * 查询列表
     *
     * @param data
     * @param oclass
     * @return
     * @throws Exception
     */
    public List<Object> queryObjectList(Map data, Class oclass) throws Exception;

    /**
     * 查询对象列表
     *
     * @param data    查询条件
     * @param oclass  类名
     * @param orderBy 排序语句
     * @return
     * @throws Exception
     */
    public List<Object> queryObjectList(Map data, Class oclass, String orderBy) throws Exception;

    /**
     * 查询列表
     *
     * @param map
     * @param oclass
     * @return
     * @throws Exception
     */
    public Object queryObject(Map map, Class oclass) throws Exception;

    /**
     * 数据删除
     *
     * @param data  key 字段名 value 数据
     * @param table 表名称
     * @return
     * @throws Exception
     */
    public int deleteObject(Map data, String table) throws Exception;

    /**
     * 删除对象
     *
     * @param data
     * @param table
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteObject(Map data, String table, String id) throws SQLException;

    /**
     * 查询对象列表
     *
     * @param oclass
     * @return
     * @throws Exception
     */
    public ArrayList<Object> queryAllObject(Class oclass) throws Exception;

    /**
     * 新增对象
     *
     * @param o
     */
    public int addObject(Object o);

    /**
     * 更新对象
     *
     * @param o
     */
    public int updateObject(Object o);

    /**
     * 删除对象
     *
     * @param o
     * @return
     */
    public int deleteObject(Object o);

    /**
     * 批量删除
     *
     * @return
     */
    public int batchDelete(List<Object> objs);

}
