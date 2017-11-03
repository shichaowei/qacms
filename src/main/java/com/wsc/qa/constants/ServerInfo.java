package com.wsc.qa.constants;

public class ServerInfo {

	public static final String sshname="root";
	public static final String sshpwd="Tairan@2017";
	public static final String anyproxyRulePath="/root/AnyProxy/rules";
	public static final String restartanyproxyShellMode="/root/AnyProxy/restartanyproxy.sh /root/AnyProxy/rules/%s &";
	public static final String[] changeDubbotimeIps={ "10.200.141.33", "10.200.141.44", "10.200.141.38" };
	public static final String[] changDubboDbtimeIps={ "10.200.141.33", "10.200.141.44", "10.200.141.38", "10.200.141.45" };
	public static final String[] changDubboResttimeIps={ "10.200.141.33", "10.200.141.44", "10.200.141.38","10.200.141.36", "10.200.141.41", "10.200.141.40"};
	public static final String[] changDubboRestDbtimeIps={  "10.200.141.33", "10.200.141.44", "10.200.141.38","10.200.141.36", "10.200.141.41", "10.200.141.40", "10.200.141.45" };
	//ssh相关变量
	public static String quartzIpadd="10.200.141.38";
	public static String stopquartzCmd="/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh stop";
	public static String restartquartzCmd="/usr/local/restart-dubbo-quartz-auto.sh";

	public static String mockServerIp="10.200.141.37";


}
