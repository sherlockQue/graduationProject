package com.a.modules.mis.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.DictDao;
import com.a.modules.mis.entity.Dict;


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


}