package com.a.modules.mis.controller;

import java.util.Arrays;
import java.util.Map;


import com.a.common.core.ControllerAide;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.mis.entity.StuScore;
import com.a.modules.mis.service.StuScoreServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("/mis/stuscore")
public class StuScoreController {
    @Autowired
    private StuScoreServiceImpl stuScoreService;

    /**
     * 列表,当前登陆学生的历年素拓分
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:student")
    public R list(@RequestParam Map<String, Object> params){

        Long stuId = ControllerAide.getUserId();
        params.put("stuId",stuId+"");
        PageUtils page = stuScoreService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   //@RequiresPermissions("mis:stuscore:info")
    public R info(@PathVariable("id") Long id){
		StuScore stuScore = stuScoreService.getById(id);
        return R.ok().put("entity", stuScore);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:stuscore:save")
    public R save(@RequestBody StuScore stuScore){
		stuScoreService.save(stuScore);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:stuscore:update")
    public R update(@RequestBody StuScore stuScore){
		stuScoreService.updateById(stuScore);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:stuscore:delete")
    public R delete(@RequestBody Long[] ids){
		stuScoreService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
