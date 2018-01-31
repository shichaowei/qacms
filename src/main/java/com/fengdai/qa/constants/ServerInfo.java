package com.fengdai.qa.constants;

public class ServerInfo {

	public static final String sshname="root";
	public static final String sshpwd="Tairan@2017";
	public static final String anyproxyRulePath="/root/AnyProxy/rules";
	public static final String restartanyproxyShellMode="/root/AnyProxy/restartanyproxy.sh /root/AnyProxy/rules/%s &";
	public static final String[] changeDubbotimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67" };
	public static final String[] changDubboDbtimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67", "10.200.141.72" };
	public static final String[] changDubboResttimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67","10.200.141.66", "10.200.141.69", "10.200.141.68"};
	public static final String[] changDubboRestDbtimeIps={ "10.200.141.62", "10.200.141.71", "10.200.141.67","10.200.141.66", "10.200.141.69", "10.200.141.68", "10.200.141.72" };
	public static final String[] changeServicetimeIps={ "10.200.141.51", "10.200.141.97" };
	public static final String[] changeServiceDbtimeIps={ "10.200.141.51", "10.200.141.97","10.200.141.58"};
	//ssh相关变量
	public static String quartzIpadd="10.200.141.67";
	public static String stopquartzCmd="/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh stop";
	public static String restartquartzCmd="/usr/local/restart-dubbo-quartz-auto.sh";
	//nc相关 部署的时候默认nc监听服务器也是工程项目部署的服务器
	public static String ncServerLogDir="/root/serverlog/";
	public static String ncServerFundCmd="cd /root/serverlog;nc -l 1567 > fund.log";
	public static String ncServerLoanCmd="cd /root/serverlog;nc -l 1568 > loan.log";
	public static String ncServerFinanceCmd="cd /root/serverlog;nc -l 1569 > finance.log";
	public static String ncServerDockCmd="cd /root/serverlog;nc -l 1570 > dock.log";

	public static String ncStopServerCmd="ps -ef | grep nc | grep -v grep| grep -v hbase|cut -c 10-15| xargs kill -9;cd /root/serverlog;rm -rf logs.zip ;zip -r logs.zip *";
	public static String ncClientFundCmdTemplate="tail -f  /usr/local/dubbo-funds-3.0.BUILD-SNAPSHOT/%s | nc 10.200.141.37 1567 &";
	public static String ncClientLoanCmdTemplate="tail -f  /usr/local/spring-boot/fd-loan/%s | nc 10.200.141.37 1568 &";
	public static String ncClientFinanceCmdTemplate="tail -f  /usr/local/spring-boot/fd-finance/%s | nc 10.200.141.37 1569 &";
	public static String ncClientDockCmdTemplate="tail -f  /usr/local/spring-boot/fd-dock/%s | nc 10.200.141.37 1570 &";
	//服務器地址列表
	public static String NCfundClientIp="10.200.141.55";
	public static String NCloanClientIp="10.200.141.53";
	public static String NCfinanceClientIp="10.200.141.52";
	public static String NCdockClientIp="10.200.141.51";

	public static String mockServerIp="10.200.141.37";
	public static String NCServerIp="10.200.141.37";
	//獲取文件夾的文件 nohop.out除外
	public static String getFileNames="ls | grep -v nohup.out|grep .log";
	public static String ncfdfundLogDir="/usr/local/dubbo-funds-3.0.BUILD-SNAPSHOT/";
	public static String ncfdloanLogDir="/usr/local/spring-boot/fd-loan/";
	public static String ncfdfinanceLogDir="/usr/local/spring-boot/fd-finance/";
	public static String ncfddockLogDir="/usr/local/spring-boot/fd-dock/";

	public static void main(String[] args) {



	}


}
