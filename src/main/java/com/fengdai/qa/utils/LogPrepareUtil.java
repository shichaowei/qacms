package com.fengdai.qa.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hzweisc
 *
 */
public abstract class LogPrepareUtil {

	private static final Logger logger = LoggerFactory.getLogger(LogPrepareUtil.class);
	private Long startTime;// 开始时间

	private Long endTime;// 结束时间

	private Long execMesc;// 耗时(毫秒)

	private Object[] params;// 方法参数
	/**
	 * @param params
	 *            方法参数
	 */
	public LogPrepareUtil(Object... params) {
		this.params = params;
		this.startTime = new Date().getTime();
		try {
			process();
			this.endTime = new Date().getTime();
			this.execMesc = endTime - startTime;
			logger.info("耗时"+execMesc+"ms");
			logger.info("耗时"+execMesc+"ms");
		} catch (Exception ex) {
			// ExceptionLog exceptionLog = new ExceptionLog();
			//
			// exceptionLog.setMethodParams(ObjectUtils.compress(GSON.toJson(params)));
			//
			// StackTraceElement[] stack =
			// Thread.currentThread().getStackTrace();
			// exceptionLog.setClazz(stack[3].getClassName());
			// exceptionLog.setMethodName(stack[3].getMethodName());
			//
			// exceptionLog.setExceptionCode("");
			// exceptionLog.setDetailMsg(ObjectUtils.compress(ex.getMessage()));
			//
			// send(exceptionLog);

			throw ex;
		}
	}


	/**
	 *
	 */
	public abstract void process();


}
