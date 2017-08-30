package com.wsc.qa.constraint;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;

/**
 * @Class Verifiable
 * @Description 可被校验的接口
 * @Author JieHong
 * @Date 2016年5月23日 下午3:32:39
 * @param <A>
 * @param <T>
 */
public interface Verifiable<A extends Annotation, T> extends ConstraintValidator<A, T> {

	/**
	 * @Title canVerify
	 * @Description 可以被校验
	 * @Author JieHong
	 * @Date 2016年5月23日 下午3:32:25
	 * @param clazz
	 * @return
	 */
	public boolean canVerify(Class<? extends Annotation> clazz);

	/**
	 * @Title verify
	 * @Description 校验
	 * @Author JieHong
	 * @Date 2016年5月23日 下午3:32:58
	 * @param annotation
	 * @param value
	 * @return true校验通过，false校验失败
	 */
	public boolean verify(A annotation, T value);

}
