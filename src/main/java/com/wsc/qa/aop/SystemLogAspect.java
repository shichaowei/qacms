package com.wsc.qa.aop;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.wsc.qa.annotation.Log;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.utils.GetUserUtil;


/**
 * @author weishichao
 * @E-mail: email
 * @version
 * @desc 切点类
 */

@Aspect
@Component
public class SystemLogAspect {


	@Resource
	private OperLogService operLogServiceImpl;

    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //Controller层切点
    @Pointcut("execution (* com.wsc.qa.web.controller..*(..))")
    public  void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     * @throws Throwable
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        logger.info("==========执行controller-syslog前置通知===============");
        if(logger.isInfoEnabled()){
            logger.info("before " + joinPoint);
        }
        getOperLog2modeHeader(joinPoint);

    }

	/**
	 * 对返回去的modemap加上username lastoperaInfo属性（header.ftl有username属性 index有lastoperaInfo）
	 *
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	private void getOperLog2modeHeader(JoinPoint joinPoint) throws Exception {
		logger.info("username::"+GetUserUtil.getUserName(getRequest(joinPoint)));
		//map中不指定username，freemarker从session中取
//		getModelMap(joinPoint).addAttribute("userName",GetUserUtil.getUserName(getRequest(joinPoint)));
		getModelMap(joinPoint).addAttribute("lastoperaInfo",operLogServiceImpl.getLastOper());
		logger.info("operlog is ::"+operLogServiceImpl.getLastOper().getUsername());

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
	 * 获取参数ModelMap
	 *
	 * @param point
	 * @return
	 */
	private ModelMap getModelMap(JoinPoint point) {
		Object[] args = point.getArgs();
		for (Object obj : args) {
			if (obj instanceof ModelMap)
				return (ModelMap) obj;
		}
		return null;
	}


    //配置controller环绕通知,使用在方法aspect()上注册的切入点
   /*   @Around("controllerAspect()")
      public void around(JoinPoint joinPoint){
          logger.info("==========开始执行controller-syslog环绕通知===============");
          long start = System.currentTimeMillis();
          try {
              ((ProceedingJoinPoint) joinPoint).proceed();
              long end = System.currentTimeMillis();
              if(logger.isInfoEnabled()){
                  logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
              }
              logger.info("==========结束执行controller-syslog环绕通知===============");
          } catch (Throwable e) {
              long end = System.currentTimeMillis();
              if(logger.isInfoEnabled()){
                  logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
              }
          }
      }
    */
    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public  void after(JoinPoint joinPoint) {
        String ip = "127.0.0.1";
         try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
             for (Method method : methods) {
                 if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                     if (clazzs.length == arguments.length && null != method.getAnnotation(Log.class)) {
                    	 if (null != method.getAnnotation(Log.class)) {
                             operationType = method.getAnnotation(Log.class).operationType();
                             operationName = method.getAnnotation(Log.class).operationName();
                        }
                         break;
                    }

                }
            }
            //*========控制台输出=========*//
            logger.info("=====controller后置通知开始=====");
            logger.info("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
            logger.info("方法描述:" + operationName);
            logger.info("请求IP:" + ip);
            logger.info("=====controller后置通知结束=====");
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e);
        }
    }

/*    //配置后置返回通知,使用在方法aspect()上注册的切入点
      @AfterReturning("controllerAspect()")
      public void afterReturn(JoinPoint joinPoint){
          logger.info("=====执行controller-syslog后置返回通知=====");
              if(logger.isInfoEnabled()){
                  logger.info("afterReturn " + joinPoint);
              }
      }*/

    /**
     * 异常通知 用于拦截记录异常日志
     *
     * @param joinPoint
     * @param e
     */
     @AfterThrowing(pointcut = "controllerAspect()", throwing="e")
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //获取请求ip
        String ip = request.getRemoteAddr();

        String params = "";
         if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
             for ( int i = 0; i < joinPoint.getArgs().length; i++) {
                params += joinPoint.getArgs()[i].toString() + ";";
            }
        }
         try {
             String targetName = joinPoint.getTarget().getClass().getName();
             String methodName = joinPoint.getSignature().getName();
             Object[] arguments = joinPoint.getArgs();
             Class targetClass = Class.forName(targetName);
             Method[] methods = targetClass.getMethods();
             String operationType = "";
             String operationName = "";
              for (Method method : methods) {
                  if (method.getName().equals(methodName)) {
                     Class[] clazzs = method.getParameterTypes();
                      if (clazzs.length == arguments.length) {
                    	  if (clazzs.length == arguments.length && null != method.getAnnotation(Log.class)) {
                              operationType = method.getAnnotation(Log.class).operationType();
                              operationName = method.getAnnotation(Log.class).operationName();
                         }
                          break;
                     }
                 }
             }
             /*========控制台输出=========*/
            logger.info("=====异常通知开始=====");
            logger.info("异常代码:" + e.getClass().getName());
            logger.info("异常信息:" + e.getStackTrace());
            logger.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
            logger.info("方法描述:" + operationName);
            logger.info("请求IP:" + ip);
            logger.info("请求参数:" + params);
            logger.info("=====异常通知结束=====");
        }  catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex);
        }
         /*==========记录本地异常日志==========*/
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", new Object[]{joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params});

    }

}