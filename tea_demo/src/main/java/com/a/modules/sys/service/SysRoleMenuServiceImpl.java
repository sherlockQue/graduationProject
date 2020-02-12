
package com.a.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.modules.sys.dao.SysRoleMenuDao;
import com.a.modules.sys.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 角色与菜单对应关系
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> {

	/**
	 * 更新关系
	 * @param roleId
	 * @param menuIdList
	 */
	@Transactional(rollbackFor = Exception.class)
	public void AsaveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		this.AdeleteBatchByRids(new Long[]{roleId});

		if(menuIdList==null||menuIdList.size() == 0){
			return ;
		}

		//再保存角色与菜单关系
		List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
		for(Long menuId : menuIdList){
			SysRoleMenu sysRoleMenuEntity = new SysRoleMenu();
			sysRoleMenuEntity.setRoleId(roleId);
			sysRoleMenuEntity.setMenuId(menuId);

			list.add(sysRoleMenuEntity);
		}
		this.saveBatch(list);
	}

	/**
	 * 根据角色ID查询菜单ID数组
	 * @param roleId
	 * @return
	 */
	public List<Long> AqueryMenuIdList(Long roleId) {
		return baseMapper.AqueryMenuIdList(roleId);
	}
	
    /**
     * 根据菜单ID查询角色ID数组
     * @param menuId
     * @return
     */
	public List<Long> AqueryRoleIdList(Long menuId) {
		return baseMapper.AqueryRoleIdList(menuId);
	}

	/**
	 * 根据角色ID数组批量删除
	 * @param roleIds
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int AdeleteBatchByRids(Long[] roleIds){
		return baseMapper.AdeleteBatchByRids(roleIds);
	}

	/**
	 * 根据菜单ID数组批量删除
	 * @param menuIds
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int AdeleteBatchByMids(Long[] menuIds){
		return baseMapper.AdeleteBatchByMids(menuIds);
	}
	
}
