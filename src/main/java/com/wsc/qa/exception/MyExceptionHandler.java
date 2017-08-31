package com.wsc.qa.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionHandler implements HandlerExceptionResolver {  
  
	@Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  
            Exception ex) {  
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex.getMessage());  
        System.out.println(ex.getCause());
          
        // 根据不同错误转向不同页面  
        if(ex instanceof BusinessException) {  
            return new ModelAndView("error", model);  
        } else {  
            return new ModelAndView("error", model);  
        }  
    }  
}  