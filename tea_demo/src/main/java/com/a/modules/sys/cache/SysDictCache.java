
package com.a.modules.sys.cache;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.common.core.Constant;
import com.a.common.utils.CacheUtils;
import com.a.modules.sys.entity.SysDict;

/**
 *  字典缓存
 *  根据type缓存 字典集合
 */
@Component
public class SysDictCache {
    @Autowired
    private CacheUtils cacheUtils;

    /**
     * 根据type放入缓存
     * @param config
     */
    public void set(String type,List<SysDict> list) {       
        String key = Constant.getSysConfigKey(type);
        cacheUtils.set(key, list);
    }

    /**
     * 根据type删除缓存
     * @param type
     */
    public void delete(String type) {
        String key = Constant.getSysConfigKey(type);
        cacheUtils.delete(key);
    }
    /**
     * 根据type得到缓存
     * @param type
     * @return
     */
    public List<SysDict> get(String type){
        String key = Constant.getSysConfigKey(type);
        return cacheUtils.getList(key, SysDict.class);
    }
}
