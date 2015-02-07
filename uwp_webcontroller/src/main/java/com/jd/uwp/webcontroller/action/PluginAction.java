package com.jd.uwp.webcontroller.action;

import com.google.gson.Gson;
import com.jd.uwp.webcontroller.service.IPluginService;
import com.jd.uwp.webcontroller.serviceimpl.PluginServiceImpl;

/**
 * 菜单加载
 * User: xushiheng
 * Date: 14-12-30
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class PluginAction extends AbstractAction {
    @Override
    public String execute(String json) throws Exception {
        IPluginService pluginService = new PluginServiceImpl();
        try {
            pluginService.loadMenus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(pluginService.getModules());
    }
}
