package com.a.common.aspect;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 使用AOP处理log 
 * 
 *   记录方法运行过程，记录到日志文件
 * 
 * @Component：spring托管类
 * @Aspect:切面类
 * 
 */
@Aspect
@Component
public class LogAspectHandler {

	// 打开日志
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 定义切入点
	 * 
	 * controller包及子包下的所有方法
	 */
	@Pointcut("execution(* com.a.modules.*.controller..*.*(..))")
	public void pointCut() {
	}

	@Pointcut("execution(* com.a.modules.mis.controller..*.*(..))")
	public void pointCut2() {
	}




	/**
	 * 记录日志，保留找一个即可
	 * 
	 * RequestLog{url='http://127.0.0.1:8080/aop/asdfafd', ip='127.0.0.1', classMethod='com.neu.t9.controller.AopController.testAop', args=[asdfafd], result=Hello asdfafd}
	 * 
	 * 
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "pointCut()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) {
		
		// 获取请求的url和ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        // 获取执行的方法和参数
        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();

		
        
		
         String  log="==============RequestLog{" +
        "url='" + url + '\'' +
        ", ip='" + ip + '\'' +
        ", classMethod='" + classMethod + '\'' +
        ", args=" + Arrays.toString(args) +
        ", result=" + result +
        ", date=" + new Date() +
        '}';
		
		
		
		
		logger.info(log);   //记录到日志文件

	}

	 

}
