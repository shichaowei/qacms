package com.wsc.qa.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.wsc.qa.utils.ExceptionUtil;

public class MyExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
	@Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>(1);
        model.put("ex", ExceptionUtil.printStackTraceToString(ex));
        logger.info("栈信息：{}",ExceptionUtil.printStackTraceToString(ex));
//        ex.printStackTrace();

        // 根据不同错误转向不同页面
        if(ex instanceof BusinessException) {
            return new ModelAndView("error", model);
        } else {
            return new ModelAndView("error", model);
        }
    }
}