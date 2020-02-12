package com.a.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * json处理工具类
 * 
 * https://blog.csdn.net/BruceLeeNumberOne/article/details/79922164
 * 
 * @author admin
 *
 */
public class JsonUtils {

    /**
     * Object转成JSON数据
     */
    public static  String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据转成Object
     */
    public static <T> T toObject(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
    
    
    /**
     * JSON数据转成List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
    
    
    /**
     * JSON数据转成JSONObject
     */
    public static JSONObject toJSONObject(String json){
        return JSON.parseObject(json);
    }
    
    
    /**
     * JSON数据转成JSONArray
     */
    public static JSONArray toJSONArray(String json){
        return JSON.parseArray(json);
    }
    
    
}
