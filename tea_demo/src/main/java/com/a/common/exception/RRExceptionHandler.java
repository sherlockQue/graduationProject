

package com.a.common.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.a.common.core.R;

/**
 *     统一异常处理器
 *
 */
@RestControllerAdvice
public class RRExceptionHandler {
	 
	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){		 
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(UnknownAccountException.class)
	public R handleUnknownAccountException(UnknownAccountException e){		 
		return R.error("用户不存在");
	}
	
	
	@ExceptionHandler(IncorrectCredentialsException.class)
	public R handleIncorrectCredentialsException(IncorrectCredentialsException e){		 
		return R.error("账号或密码不正确");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public R handleAuthenticationException(AuthenticationException e){		 
		return R.error("账户验证失败");
	}
	
	
	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){		 
		return R.error("没有权限，请联系管理员授权");
	}
	
	
	 
	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		return R.error(404, "路径不存在，请检查路径是否正确");
	}
	
	

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){		 
		return R.error("错误信息："+e.getMessage());
	}
	
	 
	 
}
