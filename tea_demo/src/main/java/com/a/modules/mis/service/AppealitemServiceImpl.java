package com.a.modules.mis.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.AppealitemDao;
import com.a.modules.mis.entity.Appealitem;


/**
 * 
 *
 */
@Service
public class AppealitemServiceImpl extends ServiceImpl<AppealitemDao, Appealitem>  {

   
    public PageUtils queryPage(Map<String, Object> params) {
    
         

        return null;
    }

}