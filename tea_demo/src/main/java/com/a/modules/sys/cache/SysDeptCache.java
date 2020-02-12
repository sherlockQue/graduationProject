
package com.a.modules.sys.cache;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.common.core.Constant;
import com.a.common.utils.CacheUtils;
import com.a.modules.sys.entity.SysDept;

/**
 * 部们缓存
 *  缓存所有部们，调用使用
 */
@Component
public class SysDeptCache {
    @Autowired
    private CacheUtils cacheUtils;
 
    /**
     * 放入缓存
     * @param list
     */
    public void set(List<SysDept> list) {
        String key = Constant.getSysDeptKey();
        cacheUtils.set(key,list);
    }
    
    /**
     * 删除缓存
     */
    public void delete() {
        String key = Constant.getSysDeptKey();
        cacheUtils.delete(key);
    }

    /**
     * 获取缓存
     * @return
     */
    public List<SysDept> listall(){
        String key = Constant.getSysDeptKey();
        return cacheUtils.getList(key, SysDept.class);
    }
}
