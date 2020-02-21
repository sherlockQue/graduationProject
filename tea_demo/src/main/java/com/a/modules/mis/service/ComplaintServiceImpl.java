package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.a.modules.mis.entity.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.a.modules.mis.dao.ComplaintDao;
import com.a.modules.mis.entity.Complaint;


/**
 * 自己的方法用A前缀，区分继承的ServiceImpl方法
 * 注意事务和缓存
 */
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintDao, Complaint> {

    @Autowired
    private DictServiceImpl dictService;


    /**
     * 分页查询,对应页面complaint.html
     */
    public PageUtils AqueryPage(Map<String, Object> params) {

        Dict dict = dictService.getById(1);
        String StGrade = dict.getGrade();  //今年的素拓
        Integer StTerm = dict.getTerm();  //第一学期
        IPage<Complaint> page = this.page(
                new Query<Complaint>().getPage(params),
                new QueryWrapper<Complaint>()
                        .eq("cp_grade", StGrade)
                        .eq("cp_term", StTerm)

        );

        return new PageUtils(page);
    }

    /**
     * 分页查询,对应页面cpresult.html
     * 已经处理过的请求
     */
    public PageUtils AqueryPage2(Map<String, Object> params) {

        Dict dict = dictService.getById(1);
        String StGrade = dict.getGrade();  //今年的素拓
        Integer StTerm = dict.getTerm();  //第一学期
        IPage<Complaint> page = this.page(
                new Query<Complaint>().getPage(params),
                new QueryWrapper<Complaint>()
                        .eq("cp_grade", StGrade)
                        .eq("cp_term", StTerm)
                        .eq("cp_status", 2)
                        .or()
                        .eq("cp_status", 3)
        );

        return new PageUtils(page);
    }

    /**
     * 获得未处理的学生申诉项目，list，对应页面disposeComplaint.html
     */
    public PageUtils AqueryPage3(Map<String, Object> params, Page page) {

        Dict dict = dictService.getById(1);
        String StGrade = dict.getGrade();  //今年的素拓
        Integer StTerm = dict.getTerm();  //第一学期
        String gradeN = (String) params.get("nian");
        String college = (String) params.get("xue");
        page.setRecords(this.baseMapper.AqueryCp(page, StGrade, StTerm, gradeN, college));
        return new PageUtils(page);

    }

    /**
     * @param ids        主键
     * @param flag       true为通过，false为不通过
     * @param cpChargeid 负责人id
     * @param cpDealReason 原因
     * @return
     */
    public boolean isAppeal(Long[] ids, boolean flag, Long cpChargeid, String cpDealReason) {
        //当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Complaint complaint = new Complaint();

        complaint.setCpStatus(3);
        if (flag){
            complaint.setCpStatus(2);
        }
        complaint.setCpDealreason(cpDealReason);
        complaint.setCpDealtime(localDateTime);
        complaint.setCpChargeid(cpChargeid);

        for (Long id :ids){
            complaint.setId(id);
            this.updateById(complaint);
        }

        return true;
    }
}