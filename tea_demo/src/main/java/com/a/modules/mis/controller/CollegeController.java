package com.a.modules.mis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.mis.entity.College;
import com.a.modules.mis.service.CollegeServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("/mis/college")
public class CollegeController {
    @Autowired
    private CollegeServiceImpl collegeService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:college:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = collegeService.AqueryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping("/showclass/{nian}/{xue}")
    public  R showclass(@PathVariable("nian")String nian,@PathVariable("xue")String xue){

        List<College> college=collegeService.getmClass(nian,xue);
        return R.ok().put("college",college);
    }




    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   //@RequiresPermissions("mis:college:info")
    public R info(@PathVariable("id") Long id){
		College college = collegeService.getById(id);
        return R.ok().put("entity", college);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:college:save")
    public R save(@RequestBody College college){
		collegeService.save(college);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:college:update")
    public R update(@RequestBody College college){
		collegeService.updateById(college);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:college:delete")
    public R delete(@RequestBody Long[] ids){
		collegeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
