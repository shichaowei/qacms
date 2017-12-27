package com.wsc.qa.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.wsc.qa.utils.ExceptionUtil;

@ControllerAdvice
public class MyExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "ops";
	private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

	 /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
	@ExceptionHandler(value = RuntimeException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception ex) throws Exception{
		logger.info("栈信息：{}",ExceptionUtil.printStackTraceToString(ex));
		ModelAndView mav = new ModelAndView();
		mav.addObject("ex",ExceptionUtil.printStackTraceToString(ex));
		mav.addObject("url",request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
//        ex.printStackTrace();
        return mav;

    }
}