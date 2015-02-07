package com.jd.uwp.accesscontrol.dao;

import com.jd.uwp.core.db.mysql.dao.BaseDao;

/**
 * 用户数据库操作类
 * User: xushiheng
 * Date: 14-12-24
 * Time: 上午10:54
 */
public interface UserDao extends BaseDao {
    /**
     * 登陆用户名 密码检查
     *
     * @param userName 用户名
     * @param password 密码
     */
    public void checkUser(String userName, String password);
}
