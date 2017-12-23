package com.wsc.qa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hzweisc
 * @description 校验meta
 * @version 1.0.0.0
 * @param str
 * @return
 */
public class ValidateUtil {


	public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        /**
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        **/
        b=Pattern.matches("^[1][3,4,5,7,8][0-9]{9}$", str);
        return b;
    }

    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;

        //p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        //p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if(str.length() >9)
        {   //m = p1.matcher(str);
           //b = m.matches();
        	b=Pattern.matches("^[0][1-9]{2,3}-[0-9]{5,10}$", str);
        }else{
            //m = p2.matcher(str);
           //b = m.matches();
        	b=Pattern.matches("^[1-9]{1}[0-9]{5,8}$", str);
        }
        return b;
    }

}
