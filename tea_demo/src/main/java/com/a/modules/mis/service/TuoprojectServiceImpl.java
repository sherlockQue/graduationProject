package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.TuoprojectDao;
import com.a.modules.mis.entity.Tuoproject;


/**
 * 
 *
 */
@Service
public class TuoprojectServiceImpl extends ServiceImpl<TuoprojectDao, Tuoproject>  {

   
    public PageUtils queryPage(Map<String, Object> params) {

        String username = (String) params.get("username");


        IPage<Tuoproject> page = this.page(new Query<Tuoproject>().getPage(params),
                new QueryWrapper<Tuoproject>().like(StringUtils.isNotBlank(username), "username", username));


        return new PageUtils(page);
    }

/*
*
* 下拉菜单
* */
    public List<Tuoproject>  getItems(String tuoId){

        QueryWrapper<Tuoproject> queryWrapper = new QueryWrapper<>();

        queryWrapper.likeRight("tuo_Id",tuoId);

        return this.baseMapper.selectList(queryWrapper);
    }

}