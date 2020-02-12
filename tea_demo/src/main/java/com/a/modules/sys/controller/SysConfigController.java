

package com.a.modules.sys.controller;


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
import com.a.modules.sys.entity.SysConfig;
import com.a.modules.sys.service.SysConfigServiceImpl;
 

/**
 * 系统配置信息
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController  {
	@Autowired
	private SysConfigServiceImpl sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){		
		PageUtils page = sysConfigService.AqueryPage(params);
		return R.ok().put("page", page);
	}
	
	
	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfig config = sysConfigService.getById(id);		
		return R.ok().put("config", config);
	}
	
	
	/**
	 * 根据key获取value
	 * {
    "msg": "success",
    "code": 0,
    "value": "{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}"
}
	 */
	@GetMapping("/getVbyK/{key}")
	@RequiresPermissions("sys:config:getVbyK")
	public R getVbyK(@PathVariable("key") String key){
		String value = sysConfigService.AgetValue(key);		
		return R.ok().put("value", value);
	}
	
	/**
	 * 保存配置
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfig config){		
		sysConfigService.Asave(config);		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfig config){		
		sysConfigService.Aupdate(config);		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.Adelete(ids);		
		return R.ok();
	}

}
