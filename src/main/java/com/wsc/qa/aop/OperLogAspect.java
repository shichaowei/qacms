package com.wsc.qa.aop;

import java.lang.reflect.Method;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants;
import com.wsc.qa.meta.OperaLog;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.utils.GetUserUitl;


@Aspect
@Component
public class OperLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(OperLogAspect.class);
	@Resource
	private OperLogService operLogServiceImpl;
	

	// Controller层切点
	@Pointcut("execution (* com.wsc.qa.web.controller..*(..))")
	public void controllerAspect() {
	}

	/**
	 * 获取方法的中文备注____用于记录用户的操作日志描述
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	private OperaLog getOperaRemark(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();

		OperaLog operaLog = new OperaLog();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					OperaLogComment methodCache = method.getAnnotation(OperaLogComment.class);
					if (methodCache != null && !("").equals(methodCache.remark())) {
						Calendar cal = Calendar.getInstance();// 取当前日期
						operaLog.setOpertype(methodCache.remark());
						operaLog.setUsername(GetUserUitl.getUserName(getRequest(joinPoint)));
						operaLog.setOpertime(CommonConstants.TIMEFORMART.format(cal.getTime()));
						return operaLog;
					}
					break;
				}
			}
		}
		System.out.println("sfdfd");
		return null;
	}

	/**
	 * 获取参数request
	 * 
	 * @param point
	 * @return
	 */
	private HttpServletRequest getRequest(JoinPoint point) {
		Object[] args = point.getArgs();
		for (Object obj : args) {
			if (obj instanceof HttpServletRequest)
				return (HttpServletRequest) obj;
		}
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}



	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 * @throws Exception 
	 */
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) throws Exception {
		
		OperaLog operaLog = getOperaRemark(joinPoint);
		if(null != operaLog) {
			
			operLogServiceImpl.insertOperLog(operaLog.getUsername(), operaLog.getOpertype());
		}
	}

}
