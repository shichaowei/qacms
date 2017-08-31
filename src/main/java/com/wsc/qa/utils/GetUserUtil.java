package com.wsc.qa.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;



public class GetUserUtil {
	
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if ( !StringUtils.isEmpty(userName)) {
			return userName;
		}else {
			return null;
		}
	}

}
