package com.jd.uwp.core.db.service;

import java.util.HashMap;
import java.util.List;


public interface IdService extends BaseService {
    public int getNextId(String tableName, String fieldName);

    public List<HashMap<String, Object>> sqlCompute(String sql);
}
