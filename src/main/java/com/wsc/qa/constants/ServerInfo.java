package com.wsc.qa.constants;

public class ServerInfo {
	
	public static final String sshname="root";
	public static final String sshpwd="Tairantest@123098";
	public static final String anyproxyRulePath="/root/AnyProxy/rules";
	public static final String restartanyproxyShellMode="/root/AnyProxy/restartanyproxy.sh /root/AnyProxy/rules/%s &";
	public static final String[] changeDubbotimeIps={ "172.30.248.31", "172.30.248.217", "172.30.249.243" };
	public static final String[] changDubboDbtimeIps={ "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.249.242" };
	public static final String[] changDubboResttimeIps={ "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.250.25","172.30.248.218", "172.30.251.190" };
	public static final String[] changDubboRestDbtimeIps={ "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.250.25",	"172.30.248.218", "172.30.251.190", "172.30.249.242" };


}
