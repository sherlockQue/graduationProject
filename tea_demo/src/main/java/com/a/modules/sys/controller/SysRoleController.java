
package com.a.modules.sys.controller;

import java.time.LocalDateTime;
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

import com.a.common.core.ControllerAide;
import com.a.common.core.R;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.entity.SysRole;
import com.a.modules.sys.service.SysRoleServiceImpl;
 

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
	@Autowired
	private SysRoleServiceImpl sysRoleService;
 
 
	
	/**
	 * 根据参数查询角色列表，分页
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.AqueryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表，选择角色
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		List<SysRole> list = sysRoleService.list();
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRole role=sysRoleService.AgetRole(roleId);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRole role){
		role.setCreateTime(LocalDateTime.now());
		role.setCreateBy(ControllerAide.getUserId());
		
		sysRoleService.Asave(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRole role){
		role.setCreateTime(LocalDateTime.now());
		role.setCreateBy(ControllerAide.getUserId());		
		sysRoleService.Aupdate(role);		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.Adelete(roleIds);
		
		return R.ok();
	}
}
