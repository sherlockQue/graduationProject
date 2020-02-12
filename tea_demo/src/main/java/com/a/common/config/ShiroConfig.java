package com.a.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.a.common.core.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {

	/**
	 * 这里使用EhCacheManager缓存
	 * 
	 * 将会缓存用户信息和权限信息
	 * 
	 * 使用redis作为缓存参考
	 * https://blog.csdn.net/qq_34021712/article/details/80774649
	 * 
	 * @return
	 */
	@Bean
    public EhCacheManager ehCacheManager(){
		 //将ehcacheManager转换成shiro包装后的ehcacheManager对象
       EhCacheManager em  = new EhCacheManager();
       em.setCacheManagerConfigFile("classpath:ehcache.xml");
       return em;
    }
	
	
 
	
	/**
	 * thymleaf使用标签相关
	 * @return
	 */
	@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
	
	
	 /**
     * 单机环境，session交给shiro管理,重启后跳到登陆页
     */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		 sessionManager.setSessionValidationSchedulerEnabled(true);
	     sessionManager.setSessionIdUrlRewritingEnabled(false);
	     sessionManager.setSessionValidationInterval(3600000L);
	 
	        
		// 设置session过期时间3600s
		sessionManager.setGlobalSessionTimeout(3600000L); // session过期时间(单位秒) 默认1800s(30min)
		
		 
		
		return sessionManager;
	}

	/**
	 * 得到SecurityManager
	 * 
	 * 
	 * @param userRealm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(EhCacheManager ehCacheManager,UserRealm userRealm, SessionManager sessionManager) {

		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

		securityManager.setRealm(userRealm);
		 //注入缓存管理器
        securityManager.setCacheManager(ehCacheManager);
        
        securityManager.setSessionManager(sessionManager);

		return securityManager;
	}

	/**
	 * 4.shiroFilter过滤器，对路径过滤，进行权限验证 • 默认登录的 URL：身份认证失败会访问该 URL。 • 认证成功之后要跳转的 URL。 •
	 * 权限认证失败后要跳转的 URL。 • 需要拦截或者放行的 URL：这些都放在一个 Map 中。
	 * 
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		// 产生过滤器
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

		// 自定义securityManager
		shiroFilter.setSecurityManager(securityManager);

		// 设置默认登录的url，身份认证失败会访问该url
		shiroFilter.setLoginUrl("/login");

		// 设置成功之后要跳转的链接,ajax跳转，这里不需
		// shiroFilter.setSuccessUrl("/index");

		// 设置未授权界面，权限认证失败会访问该url
		shiroFilter.setUnauthorizedUrl("/error");

		// LinkedHashMap是有序的，进行顺序拦截器配置
		Map<String, String> filterMap = new LinkedHashMap<>();

		// 设置可以匿名访问，可以根据实际情况自己添加，放行一些静态资源等
		filterMap.put("/static/**", "anon");
        filterMap.put("/uploadImage/**", "anon");
		filterMap.put("/login", "anon"); // 登录url 放行
		filterMap.put("/sys/login", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/captcha.jpg", "anon");
		filterMap.put("/error", "anon");

		// 设置验证访问 所有url必须通过认证才可以访问，这行代码必须放在所有权限设置的最后
		//开发时关闭，不认证
		filterMap.put("/**", "authc");

		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}

	/**
	 * 管理shiro bean生命周期
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
 * 
	 * LifecycleBeanPostProcessor 就是通过上述三个方法对Initializable和Destroyable这两个类的init方法和destroy方法进行内部调用来实现bean 的生命周期控制。
 
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/*
	 *  支持shiro注解
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
	    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
	    advisorAutoProxyCreator.setProxyTargetClass(true);
	    return advisorAutoProxyCreator;
	}
	
	/**
	 * 需要开启Shiro AOP注解支持
	 * 
	 * @RequiresPermissions
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

}