package com.a.modules.mis.dao;

import com.a.modules.mis.entity.Complaint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;
import javafx.scene.control.Pagination;


import java.util.List;

/**
 *
 *
 */
public interface ComplaintDao extends BaseMapper<Complaint> {

    /**
     *  按要求查询需要申诉的素拓项
     * @param page 自动分页
     * @param grade 当前年度
     * @param term 当前学期
     * @param graden 第几届，如2016级
     * @param college 学院
     * @return
     */
    List<Complaint> AqueryCp(Page page,
                             @Param(value = "grade") String grade,
                             @Param(value = "term") Integer term,
                             @Param(value = "graden") String graden,
                             @Param(value = "college") String college
    );

}
