package com.wsc.qa.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
	
	@Before("execution(* com.wsc.qa..*.*(..))")
	private void beforeDolog(JoinPoint jp) {
		System.out.println("sdfdddddd"+jp.toString());
	}
}
