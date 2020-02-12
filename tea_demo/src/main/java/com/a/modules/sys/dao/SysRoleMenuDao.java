

package com.a.modules.sys.dao;

import java.util.List;

import com.a.modules.sys.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 角色与菜单对应关系
 *
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> AqueryMenuIdList(Long roleId);
	
	
	/**
	 * 根据菜单ID，获取角色ID列表
	 */
	List<Long> AqueryRoleIdList(Long  menuId);

	
	/**
	 * 根据角色ID数组，批量删除
	 */
	int AdeleteBatchByRids(Long[] roleIds);
	
	/**
	 * 根据菜单ID数组，批量删除
	 */
	int AdeleteBatchByMids(Long[] menuIds);
}
