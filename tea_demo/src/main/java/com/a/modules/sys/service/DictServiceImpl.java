package com.a.modules.sys.service;

import com.a.common.core.Query;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.sys.dao.DictDao;
import com.a.modules.sys.entity.Dict;

import java.util.Map;


/**
 * 
 
    自己的方法用A前缀，区分继承的ServiceImpl方法
 *注意事务和缓存
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict>  {

     /**
     * 分页查询
     */
     public PageUtils AqueryPage(Map<String, Object> params) {

         IPage<Dict> page = this.page(new Query<Dict>().getPage(params),
                 new QueryWrapper<Dict>());
         return new PageUtils(page);
     }


}