
package com.a.modules.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.common.core.Query;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.dao.SysRoleDao;
import com.a.modules.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 角色
 *
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> {
	@Autowired
	private SysRoleMenuServiceImpl sysRoleMenuService;
	@Autowired
	private SysUserRoleServiceImpl sysUserRoleService;

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	public PageUtils AqueryPage(Map<String, Object> params) {
		String roleName = (String) params.get("roleName");

		IPage<SysRole> page = this.page(new Query<SysRole>().getPage(params),
				new QueryWrapper<SysRole>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));

		return new PageUtils(page);
	}

	/**
	 * 查询
	 * 
	 * @param roleId
	 * @return
	 */
	public SysRole AgetRole(Long roleId) {

		SysRole role = this.getById(roleId);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.AqueryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		return role;
	}

	/**
	 * 保存
	 * 
	 * @param role
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Asave(SysRole role) {

		this.save(role);

		// 保存角色与菜单关系
		sysRoleMenuService.AsaveOrUpdate(role.getRoleId(), role.getMenuIdList());

	}

	@Transactional(rollbackFor = Exception.class)
	public void Aupdate(SysRole role) {
		this.updateById(role);

		// 更新角色与菜单关系
		sysRoleMenuService.AsaveOrUpdate(role.getRoleId(), role.getMenuIdList());

	}

	@Transactional(rollbackFor = Exception.class)
	public void Adelete(Long[] roleIds) {
		// 删除角色
		this.removeByIds(Arrays.asList(roleIds));

		// 删除角色与菜单关联
		sysRoleMenuService.AdeleteBatchByRids(roleIds);

		// 删除角色与用户关联
		sysUserRoleService.AdeleteBatchByRids(roleIds);
	}

}
