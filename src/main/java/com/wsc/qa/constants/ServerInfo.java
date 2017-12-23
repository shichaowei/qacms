package com.wsc.qa.constants;

public class ServerInfo {

	public static final String sshname="root";
	public static final String sshpwd="Tairan@2017";
	public static final String anyproxyRulePath="/root/AnyProxy/rules";
	public static final String restartanyproxyShellMode="/root/AnyProxy/restartanyproxy.sh /root/AnyProxy/rules/%s &";
	public static final String[] changeDubbotimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67" };
	public static final String[] changDubboDbtimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67", "10.200.141.72" };
	public static final String[] changDubboResttimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67","10.200.141.66", "10.200.141.69", "10.200.141.68"};
	public static final String[] changDubboRestDbtimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67","10.200.141.66", "10.200.141.69", "10.200.141.68", "10.200.141.72" };
	public static final String[] changeServicetimeIps={ "10.200.141.51", "10.200.141.52" };
	public static final String[] changeServiceDbtimeIps={ "10.200.141.51", "10.200.141.52","10.200.141.58"};
	//ssh相关变量
	public static String quartzIpadd="10.200.141.67";
	public static String stopquartzCmd="/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh stop";
	public static String restartquartzCmd="/usr/local/restart-dubbo-quartz-auto.sh";

	public static String mockServerIp="10.200.141.37";

	public static void main(String[] args) {



	}


}
