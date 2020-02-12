
package com.a.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.common.core.Constant;
import com.a.modules.sys.dao.SysMenuDao;
import com.a.modules.sys.entity.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu>  {
	@Autowired
	private SysUserServiceImpl sysUserService;
	@Autowired
	private SysRoleMenuServiceImpl sysRoleMenuService;
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId
	 * @return
	 */
	public List<SysMenu> AqueryListParentId(Long parentId) {
		return this.list(new QueryWrapper<SysMenu>().eq("parent_id", parentId).orderByAsc("order_num"));		 
	}
	
	
	/**
	 * 获取不包含按钮的菜单列表
	 * @return
	 */
	public List<SysMenu> AqueryNotButtonList() {
		return this.list(new QueryWrapper<SysMenu>().ne("type", 2).orderByAsc("order_num"));
	}

    /**
     * 获取用户的所有菜单
     * @param userId
     * @return
     */
	public List<SysMenu> AgetUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return AgetAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.AqueryAllMenuId(userId);
		return AgetAllMenuList(menuIdList);
	}

 
	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Adelete(Long menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.AdeleteBatchByMids(new Long[] {menuId});
	}

	
	////////////////////////////////////////////
	public List<SysMenu> AqueryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = AqueryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenu> userMenuList = new ArrayList<>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	 
	
	
	
	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> AgetAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = AqueryListParentId(0L, menuIdList);
		//递归获取子菜单
	    AgetMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> AgetMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		
		for(SysMenu entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(AgetMenuTreeList(AqueryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
