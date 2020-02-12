

package com.a.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.common.core.R;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.entity.SysDict;
import com.a.modules.sys.service.SysDictServiceImpl;

 

/**
 * 数据字典
 *
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictServiceImpl sysDictService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.AqueryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id){
        SysDict dict = sysDictService.getById(id);        
        return R.ok().put("dict", dict);
    }
    
    /**
     * 根据type获取字典
     * @param type
     * @return
     */
    @GetMapping("/getDictByType/{type}")
    @RequiresPermissions("sys:dict:type")
    public R getDictByType(@PathVariable("type") String type){
        List<SysDict> list = sysDictService.AgetDictByType(type);        
        return R.ok().put("list", list);
    }
    

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDict dict){       
        sysDictService.save(dict);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDict dict){       
        sysDictService.updateById(dict);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
