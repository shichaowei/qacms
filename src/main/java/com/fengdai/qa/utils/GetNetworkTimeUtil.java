package com.fengdai.qa.utils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 读取网络时间
 *
 * @author SHANHY(365384722@QQ.COM)
 * @date   2015年11月27日
 */
public class GetNetworkTimeUtil {

    public static void main(String[] args) {
//        String webUrl1 = "http://www.bjtime.cn";//bjTime
//        String webUrl2 = "http://www.baidu.com";//百度
//        String webUrl3 = "http://www.taobao.com";//淘宝
//        String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
//        String webUrl5 = "http://www.360.cn";//360
//        String webUrl6 = "http://www.beijing-time.org";//beijing-time
//        System.out.println(getWebsiteDatetime(webUrl1) + " [bjtime]");
//        System.out.println(getWebsiteDatetime(webUrl2) + " [百度]");
//        System.out.println(getWebsiteDatetime(webUrl3) + " [淘宝]");
//        System.out.println(getWebsiteDatetime(webUrl4) + " [中国科学院国家授时中心]");
//        System.out.println(getWebsiteDatetime(webUrl5) + " [360安全卫士]");
//        System.out.println(getWebsiteDatetime(webUrl6) + " [beijing-time]");
    	for(int i=0;i<1000;i++) {
    		System.out.println(i);
    		System.out.println(getWebsiteDatetime());
    	}
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
//    	sdf.format(null);
    }

    /**
     * 获取指定网站的日期时间
     *
     * @param webUrl
     * @return
     * @author SHANHY
     * @date   2015年11月27日
     */
    private static String getWebsiteDatetime(String webUrl){
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            date = new Date(ld);// 转换为标准时间对象
            uc.getInputStream().close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }
	/**
	 * 从中科院授时中心获取时间，返回值为北京时间，yyyy-MM-dd HH:mm:ss 格式
	 * @return
	 */
    public static String getWebsiteDatetime() {
    	return getWebsiteDatetime("http://www.baidu.com");
    }






}