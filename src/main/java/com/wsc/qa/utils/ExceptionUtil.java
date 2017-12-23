package com.wsc.qa.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * 可以打印出完整栈信息而不是栈顶信息
 * @author hzweisc
 *
 */
public class ExceptionUtil {
	public static String printStackTraceToString(Throwable t) {
	    StringWriter sw = new StringWriter();
	    t.printStackTrace(new PrintWriter(sw, true));
	    return sw.getBuffer().toString();
	}
}
