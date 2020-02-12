package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.CollegeDao;
import com.a.modules.mis.entity.College;


/**
 * 
 
    自己的方法用A前缀，区分继承的ServiceImpl方法
 *注意事务和缓存
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeDao, College>  {

     /**
     * 分页查询
     */
    public PageUtils AqueryPage(Map<String, Object> params) {
    
        //查询条件，对应前台form搜索,根据需要修改
        String name = (String)params.get("name");

		IPage<College> page = this.page(
			new Query<College>().getPage(params),
			new QueryWrapper<College>()
				.like(StringUtils.isNotBlank(name),"name", name).orderByAsc("id")
		);
         

		return new PageUtils(page);
    }

    //下拉显示班级
    public List<College> getmClass(String nian,String xue){

		QueryWrapper<College> queryWrapper = new QueryWrapper<>();

		queryWrapper.eq("u_grade",nian).eq("u_college",xue);

		return this.baseMapper.selectList(queryWrapper);

	}



}