
package com.a.common.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.a.modules.mis.entity.Student;
import com.a.modules.mis.service.StudentServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.common.utils.EncryptUtils;
import com.a.modules.sys.entity.SysUser;
import com.a.modules.sys.service.SysUserServiceImpl;

/**
 * 认证，实现具体验证逻辑
 * 
 * • doGetAuthenticationInfo() 方法：用来验证当前登录的用户，获取认证信息。 • doGetAuthorizationInfo()
 * 方法：为当前登录成功的用户授予权限和分配角色。
 * 
 * 密码验证 ： boolean doCredentialsMatch(AuthenticationToken token,
 * AuthenticationInfo info);
 * 
 * 
 * 
 */
@Component
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserServiceImpl sysUserService;
	@Autowired
	private StudentServiceImpl studentService;

	/**
	 * 1.认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		// UsernamePasswordToken继承自AuthenticationToken
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 查询用户信息，根据用户名查出用户
		SysUser user =  sysUserService.AgetByName(token.getUsername());
		Student student =studentService.AgetOne(token.getUsername());
		// 这里抛出异常对应controller获取异常

		if(student != null){

			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(student, student.getStuPsword(),
					ByteSource.Util.bytes(student.getSalt()), getName());
			return info;

		}else {

			// 账号不存在
			if (user == null) {
				throw new UnknownAccountException();
			}
			// 账号锁定
			if (user.getStatus() == 0) {
				throw new LockedAccountException();
			}

		}
		// 验证给定主体的哈希凭据 hashedCredentials
		// 密码认证，这里有多个重载 getName() "UserRealm"
		//// sha256加密
		// String salt = RandomStringUtils.randomAlphanumeric(20);
		// user.setSalt(salt);
		// user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}

	/**
	 * 2.授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 得到当前用户
		Object userEntity = principals.getPrimaryPrincipal();
		// 权限格式如[sys:user:info, sys:user:save]
		String permsList="";
		//当管理员用户登陆时
		if(userEntity instanceof SysUser){
			SysUser user = (SysUser) userEntity;
			Long userId = user.getUserId();
			permsList= sysUserService.AqueryAllPerms(userId); // 查询用户权限
			String[] perms = permsList.split(",");
			Set<String> permsSet = new HashSet<>();
			for(String perm :perms){
				permsSet.add(perm);
			}
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setStringPermissions(permsSet); // 当前用户的权限标记集合
			return info;
		}

		Set<String> permsSet = new HashSet<>();
		permsSet.add("sys:student");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet); // 当前用户的权限标记集合
		return info;
	}

	// 配置凭证匹配器
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(EncryptUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(EncryptUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
