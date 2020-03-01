package com.a.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;


import com.a.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.sys.entity.Dict;
import com.a.modules.sys.service.DictServiceImpl;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("/sys/dict")
public class DictController {
    @Autowired
    private DictServiceImpl dictService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:dict:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dictService.AqueryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   //@RequiresPermissions("mis:dict:info")
    public R info(@PathVariable("id") Integer id){
		Dict dict = dictService.getById(id);
        return R.ok().put("entity", dict);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:dict:save")
    public R save(@RequestBody Dict dict){
		dictService.save(dict);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:dict:update")
    public R update(@RequestBody Dict dict){
		dictService.updateById(dict);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:dict:delete")
    public R delete(@RequestBody Integer[] ids){
		dictService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
