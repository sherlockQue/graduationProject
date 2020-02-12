
package com.a.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.modules.sys.dao.SysUserRoleDao;
import com.a.modules.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 用户与角色对应关系
 *
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole>  {

	/**
	 * 更新关联
	 * @param userId
	 * @param roleIdList
	 */
	@Transactional(rollbackFor = Exception.class)
	public void AsaveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.AdeleteBatchByUids(new Long[] {userId});

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//再保存用户与角色关系
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRole sysUserRoleEntity = new SysUserRole();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		this.saveBatch(list);
	}

	/**
	 * 根据用户ID，获取角色ID列表
	 */
 
	public List<Long> AqueryRoleIdList(Long userId) {
		return baseMapper.AqueryRoleIdList(userId);
	}

	
	/**
	 * 根据角色ID，获取用户ID列表
	 */
 
	public List<Long> AqueryUserIdList(Long roleId) {
		return baseMapper.AqueryUserIdList(roleId);
	}
	
	
	/**
	 * 根据角色ID数组，批量删除
	 */
	@Transactional(rollbackFor = Exception.class)
	public int AdeleteBatchByRids(Long[] roleIds){
		return baseMapper.AdeleteBatchByRids(roleIds);
	}
	
	/**
	 * 根据用户ID数组，批量删除
	 */
	@Transactional(rollbackFor = Exception.class)
	public int AdeleteBatchByUids(Long[] userIds) {
		return baseMapper.AdeleteBatchByUids(userIds);
	}
}
