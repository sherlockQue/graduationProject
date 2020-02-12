

package com.a.common.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 缓存工具类
 * 基于redis实现
 */
@Component
public class CacheUtils {
	
	//注入StringRedisTemplate
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
    
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    ////////////////////////////////////////////////string类型：opsForValue();  操作字符串
    
    public void set(String key, Object value, long expire){
    	stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(value));
        if(expire != NOT_EXPIRE){
        	stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

 

    public <T> T getObject(String key, Class<T> clazz) {
    	  String value = stringRedisTemplate.opsForValue().get(key);        
          return value == null ? null : JsonUtils.toObject(value, clazz);
    }

    
    public <T> List<T> getList(String key, Class<T> clazz) {
  	  String value = stringRedisTemplate.opsForValue().get(key);        
      return value == null ? null : JsonUtils.toList(value, clazz);
  }
    
    
     
    public JSONObject getJSONObject(String key) {
  	  String value = stringRedisTemplate.opsForValue().get(key);        
        return value == null ? null : JsonUtils.toJSONObject(value);
  }

  
  public JSONArray getJSONArray(String key) {
	  String value = stringRedisTemplate.opsForValue().get(key);        
    return value == null ? null : JsonUtils.toJSONArray(value);
}
    
    
    public void delete(String key) {
    	stringRedisTemplate.delete(key);
    }

    
    //////////////////////////////////////////////////// hash,list,set,zet类型可以接着封装
    
}
