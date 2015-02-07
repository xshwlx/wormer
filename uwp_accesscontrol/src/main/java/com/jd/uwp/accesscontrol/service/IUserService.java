package com.jd.uwp.accesscontrol.service;

import com.jd.uwp.core.db.service.BaseService;

/**
 * 用户服务
 */
public interface IUserService extends BaseService {
    /**
     * 用户登陆接口 <br>
     *
     * @param userName erp 账号或者用户名
     * @param password
     * @return
     */
    public String login(String userName, String password) throws Exception;

}
