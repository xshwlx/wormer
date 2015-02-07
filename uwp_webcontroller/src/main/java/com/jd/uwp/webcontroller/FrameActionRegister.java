package com.jd.uwp.webcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * action 注册 register
 *
 * @author xsh
 */
public class FrameActionRegister {

    public static Map<String, String> actions = new HashMap<String, String>() {{
        // 系统内置接入控制模块
        put("login", "com.jd.uwp.webcontroller.action.LoginAction");
        put("loadPlugins", "com.jd.uwp.webcontroller.action.PluginAction");
    }};

    public static String getAction(String actionName) {
        return actions.get(actionName);
    }
}
