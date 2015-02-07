package com.jd.uwp.core.po;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * PO 持久对象父类
 *
 * @author :    xushiheng
 * @version :
 * @date :
 */
public abstract class TableBean implements Serializable {

    public Map conditionMap = new HashMap();

    public Map getConditionMap() {
        return conditionMap;
    }

    public void setConditionMap(Map conditionMap) {
        this.conditionMap = conditionMap;
    }
}
