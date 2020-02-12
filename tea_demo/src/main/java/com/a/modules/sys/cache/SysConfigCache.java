
package com.a.modules.sys.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.common.core.Constant;
import com.a.common.utils.CacheUtils;
import com.a.modules.sys.entity.SysConfig;

/**
 * 系统配置缓存
 *  
 *  根据key获取value
 */
@Component
public class SysConfigCache {
    @Autowired
    private CacheUtils cacheUtils;

    /**
     * 放入
     * 同名key覆盖
     * @param config
     */
    public void saveOrUpdate(SysConfig config) {
        if(config == null){
            return ;
        }
        String key = Constant.getSysConfigKey(config.getParamKey());
        cacheUtils.set(key, config);
    }

    /**
     * 删除
     * @param ParamKey
     */
    public void delete(String ParamKey) {
        String key = Constant.getSysConfigKey(ParamKey);
        cacheUtils.delete(key);
    }

    /**
     * 获得
     * @param ParamKey
     * @return
     */
    public SysConfig get(String ParamKey){
        String key = Constant.getSysConfigKey(ParamKey);
        return cacheUtils.getObject(key, SysConfig.class);
    }
}
