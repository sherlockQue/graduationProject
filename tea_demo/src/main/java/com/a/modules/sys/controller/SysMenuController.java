
package com.a.modules.sys.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a.common.core.Constant;
import com.a.common.core.ControllerAide;
import com.a.common.core.R;
import com.a.modules.sys.entity.SysMenu;
import com.a.modules.sys.service.SysMenuServiceImpl;


/**
 * 系统菜单
 *
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController  {
	@Autowired
	private SysMenuServiceImpl sysMenuService;

	/**
	 * 1.获取用户导航菜单，根据不同用户显示不同菜单
	 */
	@GetMapping("/nav")
	public R nav(){
		List<SysMenu> menuList = sysMenuService.AgetUserMenuList(ControllerAide.getUserId());
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 2.获取所有菜单列表
	 * 
	 *  $.get(baseURL + "sys/menu/list", function(r){
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public R list(){
		List<SysMenu> menuList = sysMenuService.list(null);
		for(SysMenu SysMenu : menuList){
			SysMenu parentMenuEntity = sysMenuService.getById(SysMenu.getParentId());
			if(parentMenuEntity != null){
				SysMenu.setParentName(parentMenuEntity.getName());
			}
		}

		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 3.选择菜单(添加、修改菜单) 菜单树使用，非按钮
	 * 
	 *  $.get(baseURL + "sys/menu/select", function(r)
	 *  
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public R select(){
		//查询列表数据，非按钮
		List<SysMenu> menuList = sysMenuService.AqueryNotButtonList();
		
		//添加顶级菜单
		SysMenu root = new SysMenu();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 4.菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 5.保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public R save(@RequestBody SysMenu menu){
		//数据校验
	 
		//业务判断
		if(StringUtils.isBlank(menu.getName())){
			return R.error("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			return R.error("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				return R.error("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				return R.error("上级菜单只能为目录类型");
				 
			}
			 
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				return R.error("上级菜单只能为菜单类型");
			}
		 
		}
		
		sysMenuService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public R update(@RequestBody SysMenu menu){
		//数据校验
		 
		//业务判断		
		if(StringUtils.isBlank(menu.getName())){
			return R.error("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			return R.error("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				return R.error("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				return R.error("上级菜单只能为目录类型");
			}
			 
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				return R.error("上级菜单只能为菜单类型");
			}
		 
		}
		
		sysMenuService.updateById(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public R delete(long menuId){
		//1.判断是否系统菜单
		if(menuId <= 31){
			return R.error("系统菜单，不能删除");
		}

		//2.判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.AqueryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.Adelete(menuId);

		return R.ok();
	}
	
	 
}