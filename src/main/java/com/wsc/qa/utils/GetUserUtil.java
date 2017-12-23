package com.wsc.qa.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;



public class GetUserUtil {
	
	public static String getUserName(HttpServletRequest request) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		String userName="";
		//为了支持中文 空值的时候URLDecoder.decode会抛空指针所有要判空操作
		userName = session.getAttribute("userName")!=null?URLDecoder.decode((String) session.getAttribute("userName"), "UTF-8") : "";
		if ( !StringUtils.isEmpty(userName)) {
			return userName;
		}else {
			return null;
		}
	}

}
