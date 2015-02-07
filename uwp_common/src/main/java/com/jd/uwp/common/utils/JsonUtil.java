package com.jd.uwp.common.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

/**
 * json 工具类
 *
 * @author xushiheng (2014-1-9上午10:57:08)
 */
public class JsonUtil {
    /**
     * @return JsonObject
     */
    public static JsonObject getJsonObjectFromClient(String json) throws Exception {
        if (json == null || "".equals(json)) {
            throw new Exception();
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        }
        throw new Exception();
    }

    /**
     * @throws Exception
     */
    public static String getAction(String json) throws Exception {
        return getJsonObjectFromClient(json).get("op").getAsString();
    }

    /**
     * json 字符串转json对象
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(json).getAsJsonObject();
        return obj;
    }

    /**
     * 根据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return JsonUtil.toMap(JsonUtil.parseJson(json));
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray)
                map.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject)
                map.put((String) key, toMap((JsonObject) value));
            else
                map.put((String) key, value);
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

}
