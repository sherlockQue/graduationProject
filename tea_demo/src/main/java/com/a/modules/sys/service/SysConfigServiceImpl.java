

package com.a.modules.sys.service;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.common.core.Query;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.cache.SysConfigCache;
import com.a.modules.sys.dao.SysConfigDao;
import com.a.modules.sys.entity.SysConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> {
	@Autowired
	private SysConfigCache sysConfigCache;

     /**
      * 分页查询
      * @param params
      * @return
      */
	public PageUtils AqueryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		IPage<SysConfig> page = this.page(
			new Query<SysConfig>().getPage(params),
			new QueryWrapper<SysConfig>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
		);
        
		return new PageUtils(page);
	}
	
	/**
	 * 保存
	 * @param config
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Asave(SysConfig config) {
		this.save(config);
		sysConfigCache.saveOrUpdate(config);
	}

	/**
	 * 更新
	 * @param config
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Aupdate(SysConfig config) {
		SysConfig configEntity = this.getById(config.getId());
		sysConfigCache.delete(configEntity.getParamKey());
		this.updateById(config);
		sysConfigCache.saveOrUpdate(config);
	}
 

	/**
	 * 删除
	 * @param ids
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Adelete(Long[] ids) {
		for(Long id : ids){
			SysConfig config = this.getById(id);
			sysConfigCache.delete(config.getParamKey());
		}
		this.removeByIds(Arrays.asList(ids));
	}

    /**
     * 根据key获取value
     * @param key
     * @return
     */
	public String AgetValue(String key) {
		SysConfig config = sysConfigCache.get(key);
		if(config == null){
		    config=this.getOne(new QueryWrapper<SysConfig>().eq(StringUtils.isNotBlank(key),"param_key", key));
			sysConfigCache.saveOrUpdate(config);
		}
		return config == null ? null : config.getParamValue();
	}
 
}
