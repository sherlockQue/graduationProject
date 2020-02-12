
package com.a.modules.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a.common.core.Query;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.cache.SysDictCache;
import com.a.modules.sys.dao.SysDictDao;
import com.a.modules.sys.entity.SysDict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDict>  {
	
	@Autowired
	private SysDictCache sysDictCache;
	
    /**
     * 分页查询 
     * @param params
     * @return
     */
    public PageUtils AqueryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<SysDict> page = this.page(
            new Query<SysDict>().getPage(params),
            new QueryWrapper<SysDict>()
                .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }
    
    /**
     * 根据type获取字典列表
     * @param type
     * @return
     */
    public List<SysDict> AgetDictByType(String type){
    	List<SysDict> list=sysDictCache.get(type);
    	if(list==null) {
    		list=this.list(new QueryWrapper<SysDict>().eq("type", type));
    		sysDictCache.set(type, list);
    	}
    	return list; 	
    }
    
    public void Asave(SysDict entity) {    	 
    	this.save(entity);
    	sysDictCache.delete(entity.getType());
    }
    
    public void Aupdate(SysDict entity) {
    	this.updateById(entity);
    	sysDictCache.delete(entity.getType());
    }
    
    
    public void Adelete(Long[] ids) {
    	this.removeByIds(Arrays.asList(ids));
    	for(Long id:ids) {
    		SysDict entity=this.getById(id);
    		sysDictCache.delete(entity.getType());
    	}
    }

}
