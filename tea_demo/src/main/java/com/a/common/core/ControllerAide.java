
package com.a.common.core;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.a.modules.sys.entity.SysUser;

 
/**
 * Controller公共组件
 * Controller层使用
 */
public  class ControllerAide {
	 
	/**
	 * 得到subject，通常可以理解为当前用户对象
	 * @return
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 得到session,当前会话
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 设置session kv
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 得到session k
	 * @param key
	 * @return
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}
	
    /**
     * 得到当前user
     * @return
     */
	public static SysUser getUserEntity() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 得到当前user id
	 * @return
	 */
	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
		
	
	/**
	 * 判断登录
	 * @return
	 */
	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	/**
	 * 退出
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	/**
	 * 从session拿到验证码
	 * @param key
	 * @return
	 */
	public static Object getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		return kaptcha;
	}
	
	
 

	
}
