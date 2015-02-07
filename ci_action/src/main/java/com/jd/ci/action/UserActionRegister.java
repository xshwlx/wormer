package com.jd.ci.action;

import java.util.HashMap;
import java.util.Map;

public class UserActionRegister {
    public static Map<String, String> actions = new HashMap<String, String>() {{

    }};

    public static String getAction(String actionName) {
        return actions.get(actionName);
    }
}
