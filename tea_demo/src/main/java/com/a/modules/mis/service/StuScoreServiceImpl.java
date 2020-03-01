package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.StuScoreDao;
import com.a.modules.mis.entity.StuScore;


/**
 * 
 
    自己的方法用A前缀，区分继承的ServiceImpl方法
 *注意事务和缓存
 */
@Service
public class StuScoreServiceImpl extends ServiceImpl<StuScoreDao, StuScore>  {

     /**
     * 分页查询
     */
	 public PageUtils queryPage(Map<String, Object> params) {

		 String stuId = (String) params.get("stuId");

		 IPage<StuScore> page = this.page(new Query<StuScore>().getPage(params),
				 new QueryWrapper<StuScore>().eq("s_stuid",stuId));

		 return new PageUtils(page);
	 }

}