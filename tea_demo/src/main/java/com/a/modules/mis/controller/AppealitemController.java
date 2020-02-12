package com.a.modules.mis.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.mis.entity.Appealitem;
import com.a.modules.mis.service.AppealitemServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("mis/appealitem")
public class AppealitemController {
    @Autowired
    private AppealitemServiceImpl appealitemService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:appealitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appealitemService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{apId}")
   //@RequiresPermissions("mis:appealitem:info")
    public R info(@PathVariable("apId") Long apId){
		Appealitem appealitem = appealitemService.getById(apId);
        return R.ok().put("appealitem", appealitem);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:appealitem:save")
    public R save(@RequestBody Appealitem appealitem){
		appealitemService.save(appealitem);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:appealitem:update")
    public R update(@RequestBody Appealitem appealitem){
		appealitemService.updateById(appealitem);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:appealitem:delete")
    public R delete(@RequestBody Long[] apIds){
		appealitemService.removeByIds(Arrays.asList(apIds));
        return R.ok();
    }

}
