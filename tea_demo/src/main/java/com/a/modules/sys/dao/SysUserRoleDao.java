

package com.a.modules.sys.dao;

import java.util.List;

import com.a.modules.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户与角色对应关系
 *
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> AqueryRoleIdList(Long userId);
	
	/**
	 * 根据角色ID，获取用户ID列表
	 */
	List<Long> AqueryUserIdList(Long  roleId);


	/**
	 * 根据角色ID数组，批量删除
	 */
	int AdeleteBatchByRids(Long[] roleIds);
	
	/**
	 * 根据用户ID数组，批量删除
	 */
	int AdeleteBatchByUids(Long[] userIds);
}
