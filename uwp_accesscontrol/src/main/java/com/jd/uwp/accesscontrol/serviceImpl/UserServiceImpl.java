package com.jd.uwp.accesscontrol.serviceImpl;

import com.jd.uwp.accesscontrol.dao.UserDao;
import com.jd.uwp.accesscontrol.service.IUserService;
import com.jd.uwp.core.db.service.BaseServiceImpl;

/**
 * 用户服务接口实现
 * User: xushiheng
 * Date: 14-12-24
 * Time: 上午10:51
 */
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String login(String userName, String password) throws Exception {
        this.begin(userDao);
        try {
            userDao.checkUser(userName, password);
        } catch (Exception e) {
            this.rollback(userDao);
            throw e;
        }
        this.end(userDao);
        return null;
    }
}
