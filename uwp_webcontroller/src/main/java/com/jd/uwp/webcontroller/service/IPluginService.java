package com.jd.uwp.webcontroller.service;

import com.jd.uwp.core.db.service.BaseService;
import com.jd.uwp.webcontroller.menu.Module;

/**
 * 菜单加载服务
 * User: xushiheng
 * Date: 15-1-20
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public interface IPluginService extends BaseService{
    /**
     * 获取模块列表
     * @return
     */
    public Module[] getModules();

    /**
     * 加载模块
     * @throws Exception
     */
    public void loadMenus() throws Exception;
}
