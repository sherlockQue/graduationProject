package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.a.modules.sys.entity.SysDept;
import com.a.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.a.modules.mis.dao.StudentDao;
import com.a.modules.mis.entity.Student;


/**
 * 
 *
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student>  {



   
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");

        IPage<Student> page = this.page(new Query<Student>().getPage(params),
                new QueryWrapper<Student>().like(StringUtils.isNotBlank(username), "username", username));

        return new PageUtils(page);
    }

    //下拉显示学生
    public List<Student> AgetStudent(String nian, String xue, String mc){

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("stu_grade",nian).eq("stu_college",xue).eq("stu_class",mc);

        return this.baseMapper.selectList(queryWrapper);

    }


}