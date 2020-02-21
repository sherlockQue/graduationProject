package com.a.modules.mis.service;

import com.a.common.core.Query;
import com.a.modules.mis.entity.Complaint;
import com.a.modules.mis.entity.Tuoproject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import com.a.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.a.modules.mis.dao.StrelationDao;
import com.a.modules.mis.entity.Strelation;


/**
 *
 *
 */
@Service
public class StrelationServiceImpl extends ServiceImpl<StrelationDao, Strelation> {

    @Autowired
    private ComplaintServiceImpl complaintService;

    public PageUtils queryPage(Map<String, Object> params) {

        IPage<Strelation> page = this.page(new Query<Strelation>().getPage(params),
                new QueryWrapper<Strelation>());


        return new PageUtils(page);
    }

    /**
     * 一级认证的学生素拓项目
     */
    public PageUtils renListOne(Map<String, Object> params) {

        String StGrade = (String) params.get("StGrade");  //今年的素拓
        Integer StTerm = (Integer) params.get("StTerm");       //第一学期
        String stuId = (String) params.get("stuId");
        IPage<Strelation> page = this.page(new Query<Strelation>().getPage(params),
                new QueryWrapper<Strelation>()
                        .eq("st_grade", StGrade)
                        .eq("st_term", StTerm)
                        .eq("stu_id", stuId)
                        .eq("st_one_status", 0)
        );

        return new PageUtils(page);
    }

    /**
     *二级认证的学生素拓项目
     */
    public PageUtils renListTwo(Map<String, Object> params) {

        String StGrade = (String) params.get("StGrade");  //今年的素拓
        Integer StTerm = (Integer) params.get("StTerm");       //第一学期
        String stuId = (String) params.get("stuId");
        IPage<Strelation> page = this.page(new Query<Strelation>().getPage(params),
                new QueryWrapper<Strelation>()
                        .eq("st_grade", StGrade)
                        .eq("st_term", StTerm)
                        .eq("stu_id", stuId)
                        .eq("st_one_status", 1)
                        .eq("st_two_status",0)
        );

        return new PageUtils(page);
    }

    /**
     * 检查是否重复插入素拓项目
     */
    public boolean sameStuId(Strelation strelation) {
        QueryWrapper<Strelation> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("stu_id", strelation.getStuId())
                .eq("tuo_id", strelation.getTuoId())
                .eq("st_grade", strelation.getStGrade())
                .eq("st_term", strelation.getStTerm());
        Integer flag = this.baseMapper.selectCount(queryWrapper);
        if (flag == null || flag == 0) {
            return true;
        }
        return false;
    }

    /**
     * 处理一级认证,
     *
     * @param flag  true为通过，false为不通过
     * @param renId 认证人id
     */
    public boolean AOnePass(Long[] ids, boolean flag, Long renId) {

        LocalDateTime localDateTime = LocalDateTime.now(); //时间
        Strelation strelation = new Strelation();

        if (flag) {
            strelation.setStCheckone(renId);
            strelation.setStOneStatus(1);
            strelation.setStOneTime(localDateTime);
        } else {
            strelation.setStCheckone(renId);
            strelation.setStOneStatus(2);
            strelation.setStOneTime(localDateTime);
            complaintBox(ids);
        }
        for (Long id : ids) {
            UpdateWrapper<Strelation> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("st_id", id);
            this.baseMapper.update(strelation, updateWrapper);
        }

        return true;
    }
    /**
     * 处理二级认证,
     *
     * @param flag  true为通过，false为不通过
     * @param renId 认证人id
     */
    public boolean ATwoPass(Long[] ids, boolean flag, Long renId) {

        LocalDateTime localDateTime = LocalDateTime.now(); //时间
        Strelation strelation = new Strelation();

        if (flag) {
            strelation.setStChecktwo(renId);
            strelation.setStTwoStatus(1);
            strelation.setStTwoTime(localDateTime);
        } else {
            strelation.setStChecktwo(renId);
            strelation.setStTwoStatus(2);
            strelation.setStTwoTime(localDateTime);
            complaintBox(ids);
        }
        for (Long id : ids) {
            UpdateWrapper<Strelation> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("st_id", id);
            this.baseMapper.update(strelation, updateWrapper);
        }

        return true;
    }

    /**
     * 当有不通过的项目时，记录到申诉表
     */
    public void complaintBox(Long []ids){

        for(Long id : ids){
            Strelation strelation = this.getById(id);
            Complaint complaint =new Complaint();
            complaint.setStuId(strelation.getStuId());
            complaint.setStId(id);
            complaint.setCpGrade(strelation.getStGrade());
            complaint.setCpTerm(strelation.getStTerm());
            complaint.setCpStatus(0);
            complaintService.save(complaint);
        }

    }
}