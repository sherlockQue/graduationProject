

package com.a.modules.sys.controller;


import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.common.core.Constant;
import com.a.common.core.ControllerAide;
import com.a.common.core.R;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.entity.SysUser;
import com.a.modules.sys.service.SysUserServiceImpl;
 

/**
 * 系统用户,管理员
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController  {
	@Autowired
	private SysUserServiceImpl sysUserService;
 
	
	/**
	 * 所有用户列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.AqueryPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		return R.ok().put("user", ControllerAide.getUserEntity());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@PostMapping("/password")
	public R password(String password, String newPassword){
		//更新密码
		boolean flag = sysUserService.AupdatePassword(ControllerAide.getUserEntity(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUser user=sysUserService.Aget(userId);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUser user){

		user.setCreateTime(LocalDateTime.now());
		user.setCreateBy(ControllerAide.getUserId());
		sysUserService.Asave(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUser user){

		user.setCreateTime(LocalDateTime.now());
		user.setCreateBy(ControllerAide.getUserId());
		sysUserService.Aupdate(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, ControllerAide.getUserId())){
			return R.error("当前用户不能删除");
		}

		sysUserService.Adelete(userIds);
		
		return R.ok();
	}
}
