package com.a.modules.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.common.core.Query;
import com.a.common.utils.EncryptUtils;
import com.a.common.utils.PageUtils;
import com.a.modules.sys.dao.SysUserDao;
import com.a.modules.sys.entity.SysDept;
import com.a.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 系统用户
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> {

	@Autowired
	private SysDeptServiceImpl sysDeptService;

	/**
	 * 查询用户的所有菜单ID
	 */
	public List<Long> AqueryAllMenuId(Long userId) {
		return this.baseMapper.AqueryAllMenuId(userId);
	}

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public PageUtils AqueryPage(Map<String, Object> params) {
		String username = (String) params.get("username");

		IPage<SysUser> page = this.page(new Query<SysUser>().getPage(params),
				new QueryWrapper<SysUser>().like(StringUtils.isNotBlank(username), "username", username));

		for (SysUser SysUser : page.getRecords()) {
			SysDept sysDeptEntity = sysDeptService.getById(SysUser.getDeptId());
			SysUser.setDeptName(sysDeptEntity.getName());
		}

		return new PageUtils(page);
	}

	/**
	 * 查询用户
	 * @param userId
	 * @return
	 */
	public SysUser Aget(Long userId) {
		SysUser user = this.getById(userId);

		// 获取部们名称
		SysDept sysDeptEntity = sysDeptService.getById(user.getDeptId());
		user.setDeptName(sysDeptEntity.getName());


		return user;
	}

	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public SysUser AgetByName(String username) {
		SysUser user = this.getOne(new QueryWrapper<SysUser>().eq("username", username));

		// 获取部们名称
		if(user != null && user.getDeptId() != null) {
			SysDept sysDeptEntity = sysDeptService.getById(user.getDeptId());
			user.setDeptName(sysDeptEntity.getName());
		}
		return user;
	}
	
	
	
	/**
	 * 保存
	 * 
	 * @param user
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Asave(SysUser user) {

		// 添加用户， sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20); // 随机盐
		user.setSalt(salt);
		user.setPassword(EncryptUtils.sha256(user.getPassword(), user.getSalt()));
		this.save(user);
	}

	/**
	 * 更新
	 * 
	 * @param user
	 */
	@Transactional(rollbackFor = Exception.class)
	public void Aupdate(SysUser user) {

		SysUser userEntity = this.getById(user.getUserId());
		user.setPassword(EncryptUtils.sha256(user.getPassword(), userEntity.getSalt()));

		this.updateById(user);
	}

	/**
	 * 删除用户
	 */

	@Transactional(rollbackFor = Exception.class)
	public void Adelete(Long[] userIds) {
		// 删除用户
		this.removeByIds(Arrays.asList(userIds));

	}

	/**
	 * 修改密码
	 */

	@Transactional(rollbackFor = Exception.class)
	public boolean AupdatePassword(SysUser user, String password, String newPassword) {
		// 原密码，加盐
		password = EncryptUtils.sha256(password, user.getSalt());
		// 新密码，加盐
		newPassword = EncryptUtils.sha256(newPassword, user.getSalt());
		SysUser userEntity = new SysUser();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUser>().eq("user_id", user.getUserId()).eq("password", password));
	}

	/**
	 * 查询普通用户的所有权限
	 * @param userId  用户ID
	 */
	public String AqueryAllPerms(Long userId){

		return this.baseMapper.AqueryAllPerms(userId);
	}
	
	
	/**
	 * 查询超级管理员的所有权限
	 */
	public List<String> AqueryAllPermsOfGOD(){
		return this.baseMapper.AqueryAllPermsOfGOD();
	}
	
	
}
