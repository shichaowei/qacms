package com.wsc.qa.aop;

import java.lang.reflect.Method;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.datasource.DataSourceContextHolder;
import com.wsc.qa.datasource.DataSourceInfo;
import com.wsc.qa.meta.OperaLog;
import com.wsc.qa.meta.OperaLogCheck;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.utils.FormateDateUtil;
import com.wsc.qa.utils.GetUserUtil;


@Aspect
@Component
public class OperLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(OperLogAspect.class);
	@Resource
	private OperLogService operLogServiceImpl;

	private OperaLog operaLog ;

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
						operaLog.setOpertype(methodCache.remark().getValue());
						operaLog.setUsername(GetUserUtil.getUserName(getRequest(joinPoint)));
						operaLog.setOpertime(FormateDateUtil.format(cal.getTime()));
						operaLog.setStatus("SUCCESS");
						//测试
						OperaLogCheck.checkOperLog(operaLog);
						return operaLog;
					}
					break;
				}
			}
		}
		return null;
	}

	/**
	 * 对返回去的modemap加上username lastoperaInfo属性（header.ftl有username属性 index有lastoperaInfo）
	 *
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
//	private void addmodeHeader(JoinPoint joinPoint) throws Exception {
//		logger.info("username::"+GetUserUtil.getUserName(getRequest(joinPoint)));
//		getModelMap(joinPoint).addAttribute("userName",GetUserUtil.getUserName(getRequest(joinPoint)));
//		getModelMap(joinPoint).addAttribute("lastoperaInfo",operLogServiceImpl.getLastOper());
//
//	}

	/**
	 * 获取参数request
	 *
	 * @param point
	 * @return
	 */
	private HttpServletRequest getRequest(JoinPoint point) {
		Object[] args = point.getArgs();
		for (Object obj : args) {
			if (obj instanceof HttpServletRequest) {
				return (HttpServletRequest) obj;
			}
		}
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	/**
	 * 获取参数ModelMap
	 *
	 * @param point
	 * @return
	 */
	private ModelMap getModelMap(JoinPoint point) {
		Object[] args = point.getArgs();
		for (Object obj : args) {
			if (obj instanceof ModelMap) {
				return (ModelMap) obj;
			}
		}
		return null;
	}


	   /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
	 * @throws Exception
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        logger.info("==========执行controller-operlog前置通知===============");
//        addmodeHeader(joinPoint);
        operaLog = getOperaRemark(joinPoint);
        logger.info("==========执行controller-operlog前置通知结束===============");
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
		//切换数据库
		DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_ADMIN);
		logger.info("==========执行controller-operlog后置通知===============");
		if(null != operaLog) {
			operLogServiceImpl.insertOperLog(operaLog);
		}
		logger.info("=====controller-operlog后置通知结束=====");
	}

	 /**
     * 异常通知 用于拦截记录异常日志
     *
     * @param joinPoint
     * @param e
     */
     @AfterThrowing(pointcut = "controllerAspect()", throwing="e")
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
    	//切换数据库
 		DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_ADMIN);
    	 /*========控制台输出=========*/
         logger.info("=====执行controller-operlog异常通知=====");
         if(null != operaLog) {
        	 operaLog.setStatus("FAILED");
//        	 logger.info("id is :{}",operaLog.getId());
 			operLogServiceImpl.updateOperLogStatus(operaLog);
 		}
         logger.info("=====执行controller-operlog异常通知结束=====");

    }



}
