package com.jd.uwp.core.db.mysql.dao;

import com.jd.uwp.core.dbconnction.DBConnection;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * 基础数据库操作接实现
 *
 * @author xushiheng
 */
public class BaseDaoImpl implements BaseDao {

    private static Logger logger = Logger.getLogger(BaseDaoImpl.class);
    private String daoName = "";

    public int getNextSqlID(String tableName, String fieldName) throws SQLException {
        int id = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn.commit();
            String sql = "select getNextSEQ(?,?)";
            ps = this.getPrepareStatement(sql, true);
            ps.setString(1, tableName);
            ps.setString(2, fieldName);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            conn.commit();
            return id;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }


//		int id = ConnectionBase.getNextSqlID(tableName, idName);
//		return id;
    }

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

    private Connection conn;


    private PreparedStatement pre;


    private ResultSet rs;

    public int addObject(String[] nameList, String[] valueList, int idIndex, String table) throws SQLException {
        PreparedStatement ps = null;
        try {
            int resultInt = 0;
//			int resultInt=getNextSqlID( table , nameList[idIndex] );
//			int resultInt=getNextSqlID( table );

//			resultInt=rs.getInt(1);
//			valueList[0]=resultInt+"";
            StringBuffer sqlStr = new StringBuffer("insert into ").append(table).append("(");
            StringBuffer valueStr = new StringBuffer(" values(");


            for (int i = 0; i < nameList.length; i++) {
                if (i == 0) {
                    sqlStr.append(nameList[i]);
                    valueStr.append("?");
                } else {
                    sqlStr.append(",").append(nameList[i]);
                    valueStr.append(",?");
                }
            }
            sqlStr.append(")");
            valueStr.append(")");
            sqlStr.append(valueStr);
            ps = this.getPrepareStatement(sqlStr.toString(), true);
            for (int i = 0; i < valueList.length; i++) {
                ps.setString(i + 1, valueList[i]);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                resultInt = rs.getInt(1);
            }
            logger.debug("Insert new Object to table: " + table + " , return gernerated key is :" + resultInt);
            return resultInt;
        } finally {
            if (ps != null) ps.close();
            ps = null;
        }
    }

    public int addObject(Map data, String table, String id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
//			int resultInt = getNextSqlID( table , id ); //暂时采用自增
            int resultInt = 0;//getNextSqlID( table );
//			data.put(id,resultInt);
            StringBuffer sql = new StringBuffer("insert into " + table);
            StringBuffer fields = null;
            StringBuffer values = null;
            try {
                Iterator iter = data.keySet().iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    Object value = data.get(key);
                    if (value != null) {
                        if (fields == null) {
                            fields = new StringBuffer("(`" + key + "`");
                        } else {
                            fields.append(",`" + key + "`");
                        }
                        if (value instanceof String) {
                            value = "'" + value + "'";
                        } else if (value instanceof Number) {

                        }
                        if (values == null) {
                            values = new StringBuffer("(" + value);
                        } else {
                            values.append("," + value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sql.append(fields + ") values");
            sql.append(values + ")");
            System.out.println("sql :  " + sql);
            try {
                ps = this.getPrepareStatement(sql.toString(), true);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    resultInt = rs.getInt(1);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                logger.error(sql);
            }
            logger.debug("Insert new Object to table: " + table + " , return gernerated key is :" + resultInt);
            return resultInt;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }

    public int updateObject(Map data, String table, String id) throws SQLException {
        int resultInt = 0;
        PreparedStatement ps = null;
        try {
            StringBuffer sql = new StringBuffer("update " + table + " set ");
            String where = null;
//			System.out.println(data+"\n");
//			System.out.println("\t"+data.get(id)+"\t"+id);
            if (data.get(id).getClass().getName().indexOf("String") > -1) {
                where = "'" + data.get(id).toString() + "'";
            } else {
                where = data.get(id).toString();
            }
            where = " where " + id + "=" + where;
            data.remove(id);
            StringBuffer values = new StringBuffer();
            Iterator iter = data.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = data.get(key);
                if (value != null) {
                    if (value.getClass().getName().indexOf("String") > -1) {
                        values.append(key + "='" + value + "',");
                    } else
                        values.append(key + "=" + value + ",");
                }
            }
            values = new StringBuffer(values.substring(0, values.length() - 1));
            sql.append(values + where);
            logger.info(sql.toString());
            ps = this.getPrepareStatement(sql.toString(), true);
            resultInt = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ps = null;
        }
        return resultInt;


    }

    public int updateObject(Map data, String table, Map updatewhere) throws SQLException {
        int resultInt = 0;
        PreparedStatement ps = null;
        try {
            StringBuffer sql = new StringBuffer("update " + table + " set ");
            StringBuffer whereStr = null;
            Iterator iter = updatewhere.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = updatewhere.get(key);
                if (value != null) {
                    if (whereStr != null) {
                        whereStr.append(" and ");
                    }
                    if (whereStr == null) {
                        whereStr = new StringBuffer(" where ");
                    }
                    if (value.getClass().getName().indexOf("Integer") > -1) {
                        whereStr.append(key + "=" + value + "");
                    } else
                        whereStr.append(key + "='" + value + "'");
                }
            }
            StringBuffer values = new StringBuffer();
            iter = data.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = data.get(key);
                if (value != null) {
                    if (value.getClass().getName().indexOf("String") > -1) {
                        values.append(key + "='" + value + "',");
                    } else
                        values.append(key + "=" + value + ",");
                }
            }
            values = new StringBuffer(values.substring(0, values.length() - 1));
            sql.append(values).append(whereStr);
            logger.info(sql.toString());
            ps = this.getPrepareStatement(sql.toString(), true);
            resultInt = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ps = null;
        }
//		this.getPrepareStatement(sql.toString()).executeUpdate();
        return resultInt;
    }

    public List<Object> queryObjectList(Map data, Class oclass) throws Exception {
        return (List<Object>) queryObjectList(data, oclass, "");
    }

    public Object queryObject(Map data, Class oclass) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List valueList = new LinkedList();
        try {
            String tableName = oclass.getName().toLowerCase();
            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
            StringBuffer sql = new StringBuffer("select * from  " + tableName + " where  ");
            StringBuffer values = null;
            Iterator iter = data.keySet().iterator();

            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = data.get(key);
                if (value != null) {
                    if (values != null) {
                        values.append(" and ");
                    }
                    if (values == null) {
                        values = new StringBuffer();
                    }
                    values.append(key + "= ?");
                    valueList.add(value);
                }
            }

            sql.append(values.toString());
            ps = this.getPrepareStatement(sql.toString(), true);
            Object[] valueArray = valueList.toArray();
            for (int i = 0; i < valueArray.length; i++) {
                //数据类型遍历
                Object value = valueArray[i];
                int index = i + 1;
                if (value.getClass().getName().indexOf("String") > -1) {
                    ps.setString(index, String.valueOf(value));
                } else if (value.getClass().getName().indexOf("Integer") > -1) {
                    ps.setInt(index, (Integer) value);
                }
            }
            logger.info("query " + oclass + " :  " + ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                Object newObj = oclass.newInstance();
                Method metod = oclass.getMethod("set", ResultSet.class);
                metod.invoke(newObj, rs);
                return newObj;
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }

    public int deleteObject(Map data, String table) throws Exception {
        int code = 0;
        PreparedStatement ps = null;
        try {
            ArrayList<Object> result = new ArrayList<Object>();
            StringBuffer sql = new StringBuffer("delete  from  " + table + " where  ");
            StringBuffer values = null;
            Iterator iter = data.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = data.get(key);
                if (value != null) {
                    if (values != null) {
                        values.append(" and ");
                    }
                    if (values == null) {
                        values = new StringBuffer();
                    }
                    if (value.getClass().getName().indexOf("String") > -1) {
                        values.append(key + "='" + value + "'");
                    } else
                        values.append(key + "=" + value + "");
                }
            }
            sql.append(values.toString());
            System.out.println("sql :  " + sql.toString());
            ps = this.getPrepareStatement(sql.toString(), true);
            code = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ps = null;
        }
        return code;
    }

    public static void main(String[] are) throws Exception {
        Map data = new HashMap();
        data.put("type", 1);
        Map updatewhere = new HashMap();
        updatewhere.put("id", 33);
        updatewhere.put("name", "52");
        String table = "account";
        new BaseDaoImpl().updateObject(data, table, updatewhere);
    }

    public boolean add(Object obj) {
        return false;
    }

    public boolean del(Object obj) {
        return false;
    }

    public Connection getConn() {
        if (conn == null) {
            conn = DBConnection.getConn();
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
            }
        } else {
            try {
                if (conn.isClosed()) {
                    conn = null;
                    conn = DBConnection.getConn();
                    try {
                        conn.setAutoCommit(false);
                    } catch (SQLException e) {
                    }
                }
            } catch (SQLException e) {
                conn = null;
                conn = DBConnection.getConn();
                try {
                    conn.setAutoCommit(false);
                } catch (SQLException ex) {
                }
            }
        }
        logger.info(this.getDaoName() + "\t" + DBConnection.showInfo("mysql"));
        System.out.println(this.getDaoName() + "\t" + DBConnection.showInfo("mysql"));
        return conn;
    }

    public Connection getConnection() {
        return this.conn;
    }

    /**
     * getPrepareStatement
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPrepareStatement(String sql) throws SQLException {
        pre = conn.prepareStatement(sql);
        return this.pre;
    }

    /**
     * getPrepareStatement
     *
     * @param sql
     * @param isSync
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPrepareStatement(String sql, boolean isSync) throws SQLException {
        if (isSync) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return ps;
        }
        return getPrepareStatement(sql);
    }

    /**
     * getResultSet
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getResultSet() throws SQLException {
        if (pre != null) {
            rs = null;
            rs = pre.executeQuery();
        }
        return rs;
    }

    /**
     * getResultSet
     *
     * @param pre
     * @return
     * @throws SQLException
     */
    public ResultSet getResultSet(PreparedStatement pre) throws SQLException {
        return pre.executeQuery();
    }

    public void Close() {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (Throwable t) {
            }
        }
        ;
        if (pre != null) {
            try {
                pre.close();
                pre = null;
            } catch (Throwable t) {
            }
        }
        ;
        if (conn != null) {
            try {
                if (isCloseAfterUsed) {
                    DBConnection.closeConnection(conn);
                    logger.info(this.getDaoName() + "\t" + DBConnection.showInfo("mysql"));
                }
            } catch (Throwable t) {
            }
        }
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    ;

    public void setConn(Connection conn, boolean isCloseAfterUsed) {
        this.conn = conn;
        this.isCloseAfterUsed = isCloseAfterUsed;
    }

    ;

    public void setConn(Connection conn, boolean isCloseAfterUsed, boolean isCommitAfterUsed) {
        this.conn = conn;
        this.isCloseAfterUsed = isCloseAfterUsed;
        this.isCommitAfterUsed = isCommitAfterUsed;
    }

    ;


    public boolean commit(Connection connection) {

        try {
            if (isCommitAfterUsed) {
                connection.commit();
            }
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
            try {

//				if(!(ex instanceof SQLException)) 
//					System.out.println("pre="+pre+"\tbaseDaoImpl.rollback.Throwable="+Utility.getExceptionString( ex ) );
                connection.rollback();
            } catch (Throwable e) {
                e.printStackTrace();

            }

            return false;
        }
    }

    public boolean commit() {

        try {
            if (isCommitAfterUsed) {
                conn.commit();
            }
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
            try {

//				if(!(ex instanceof SQLException)) 
//					System.out.println("pre="+pre+"\tbaseDaoImpl.rollback.Throwable="+Utility.getExceptionString( ex ) );
                conn.rollback();
            } catch (Throwable e) {
                e.printStackTrace();

            }

            return false;
        }
    }

    public boolean rollback(Connection connection) {
        try {
            connection.rollback();
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean rollback() {
        try {
            conn.rollback();
            return true;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void unlock() {
        try {
            this.getPrepareStatement("unlock tables ", true).executeUpdate();
        } catch (Throwable e) {
        }
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    @Override
    public int deleteObject(Map data, String table, String id) throws SQLException {
        int code = 0;
        PreparedStatement ps = null;
        try {
            ArrayList<Object> result = new ArrayList<Object>();
            StringBuffer sql = new StringBuffer("delete  from  " + table + " where " + id + " = ");
            sql.append(data.get(id));
            logger.info(sql.toString());
            ps = this.getPrepareStatement(sql.toString(), true);
            code = ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            ps = null;
        }
        return code;
    }

    @Override
    public ArrayList<Object> queryAllObject(Class oclass) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ArrayList<Object> result = new ArrayList<Object>();
            String tableName = oclass.getName().toLowerCase();
            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
            StringBuffer sql = new StringBuffer("select * from  " + tableName);
            ps = this.getPrepareStatement(sql.toString(), true);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object newObj = oclass.newInstance();
                Method metod = oclass.getMethod("set", ResultSet.class);
                metod.invoke(newObj, rs);
                result.add(newObj);
            }
            return result;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }

    @Override
    public int addObject(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateObject(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteObject(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int batchDelete(List<Object> objs) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleList(String sql, ResultSetHandler handler) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = this.getPrepareStatement(sql, true);
            rs = ps.executeQuery();
            while (rs.next()) {
                handler.handle(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }

    public void handleList(PreparedStatement ps, ResultSetHandler handler) throws Exception {
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                handler.handle(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }

    @Override
    public List<Object> queryObjectList(Map data, Class oclass, String orderby)
            throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            List<Object> result = new LinkedList<Object>();
            String tableName = oclass.getName().toLowerCase();
            tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
            StringBuffer sql = new StringBuffer("select * from  " + tableName + " where  ");
            StringBuffer values = null;
            Iterator iter = data.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = data.get(key);
                if (value != null) {
                    if (values != null) {
                        values.append(" and ");
                    }
                    if (values == null) {
                        values = new StringBuffer();
                    }
                    if (value.getClass().getName().indexOf("String") > -1) {
                        values.append(key + "='" + value + "'");
                    } else
                        values.append(key + "=" + value + "");
                }
            }
            sql.append(values.toString());
            logger.info(sql.toString());
            ps = this.getPrepareStatement(sql.toString() + " " + orderby, true);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object newObj = oclass.newInstance();
                Method metod = oclass.getMethod("set", ResultSet.class);
                metod.invoke(newObj, rs);
                result.add(newObj);
            }
            return result;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            rs = null;
            ps = null;
        }
    }
}
