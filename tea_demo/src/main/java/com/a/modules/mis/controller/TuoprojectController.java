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

import com.a.modules.mis.entity.Tuoproject;
import com.a.modules.mis.service.TuoprojectServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("mis/tuoproject")
public class TuoprojectController {
    @Autowired
    private TuoprojectServiceImpl tuoprojectService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:tuoproject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tuoprojectService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{tuoId}")
   //@RequiresPermissions("mis:tuoproject:info")
    public R info(@PathVariable("tuoId") String tuoId){
		Tuoproject tuoproject = tuoprojectService.getById(tuoId);
        return R.ok().put("tuoproject", tuoproject);
    }



    /*
    * 下拉菜单取对应项
    * */
    @GetMapping("/infoMenu/{tuoId}")
    //@RequiresPermissions("mis:tuoproject:info")
    public R infoMenu(@PathVariable("tuoId") String tuoId){

        List<Tuoproject> tuoproject = tuoprojectService.getItems(tuoId);
        return R.ok().put("tuoproject", tuoproject);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:tuoproject:save")
    public R save(@RequestBody Tuoproject tuoproject){
		tuoprojectService.save(tuoproject);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:tuoproject:update")
    public R update(@RequestBody Tuoproject tuoproject){
		tuoprojectService.updateById(tuoproject);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:tuoproject:delete")
    public R delete(@RequestBody String[] tuoIds){
		tuoprojectService.removeByIds(Arrays.asList(tuoIds));
        return R.ok();
    }

}
