package com.wsc.qa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wsc.qa.constants.CommonConstants.opertype;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperaLogComment {
	/** 要执行的操作比如：add操作 **/
    public opertype remark();

}
