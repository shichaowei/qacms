package com.wsc.qa.testUtil;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.utils.NcLogUtil;
import com.wsc.qa.utils.SshUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestLog {
	// SshWatched fundServerSshWatched =new SshWatched(ServerInfo.NCServerIp,
	// ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFundCmd);
	// SshWatcher fundServerSshWatcher = new SshWatcher(fundServerSshWatched);
	// SshWatched fundClientSshWatched =new SshWatched(ServerInfo.NCfundClientIp,
	// ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncClientFundCmd);
	// SshWatcher fundClientSshWatcher = new SshWatcher(fundClientSshWatched);
	// fundServerSshWatched.closesession();
	// fundClientSshWatched.closesession();

	private static final Logger logger = LoggerFactory.getLogger(TestLog.class);
	/**
	 * 判断日志到底写入的是哪一个文件
	 * 并且开启日志传送
	 *
	 */
	public static void startLogTransfer(String ncclientip,String ncclientlogdir,String ncclientCmdTemplate) {
		String varStr = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
				"cd "+ncclientlogdir+";" + ServerInfo.getFileNames);
		for (String var : varStr.split("\n")) {
			String filetime = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
					"cd "+ncclientlogdir+";date +%s -r " + var);
			String nowtime = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
					"date +%s");
			if (Long.valueOf(nowtime) - Long.valueOf(filetime) >= 0
					&& Long.valueOf(nowtime) - Long.valueOf(filetime) <= 60 * 2) {
				System.out.println(ncclientlogdir+":当前日志进入的是" + var);
				//loan日志开始传送
				System.out.println("cmd is "+String.format(ncclientCmdTemplate, var));
				SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ncclientCmdTemplate, var));
				break;
			}
		}
	}

	public static void tibug() {
		RequestSpecification requestmantis = given().header("Cookie","PHPSESSID=a0d4fqacr12psokgi0nb1eh9f3; MANTIS_secure_session=1; MANTIS_STRING_COOKIE=6ce3db17297e1b3b7e05a17f1f6a4414d82f15ff982166d98743a77e943de4ef; MANTIS_VIEW_ALL_COOKIE=149; MANTIS_BUG_LIST_COOKIE=18672%2C18671%2C18670%2C18663");
		HashMap<String, Object> params = new HashMap<>();
		params.put("bug_report_token", "201712297bd86d87847695e3f234d1eb71038a388f1e5db6");
		params.put("m_id", 0);
		params.put("project_id", 3);
		params.put("category_id", 138);
		params.put("reproducibility", 70);
		params.put("severity", 50);
		params.put("priority", 30);
		params.put("severity", 50);
		params.put("handler_id", 0);
		params.put("summary", 1);
		params.put("handler_id", 0);
		params.put("description", 1);
		params.put("max_file_size", 5000000);
		params.put("view_state", 10);
		params.forEach((k,v)->{
			System.out.println("k:"+k+":v:"+v);
			requestmantis.multiPart(k, v,"form-data");
		});
		requestmantis.multiPart("ufile[]", new File("C:\\Users\\hzweisc\\Pictures\\2.6\\1.png"));
		String var = requestmantis.when().post("http://10.200.130.103/mantis/bug_report.php").andReturn().asString();
		System.out.println(var);
	}

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "http://10.200.141.56/";

		/*//server开启funds loan finance dock 日志监听端口
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFundCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerLoanCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFinanceCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerDockCmd);
		//funds日志开始传送
//		SshUtil.remoteRunCmd(ServerInfo.NCfundClientIp, ServerInfo.sshname, ServerInfo.sshpwd,ServerInfo.ncClientFundCmd);
		startLogTransfer(ServerInfo.NCfundClientIp, ServerInfo.ncfdfundLogDir,ServerInfo.ncClientFundCmdTemplate);
		startLogTransfer(ServerInfo.NCloanClientIp, ServerInfo.ncfdloanLogDir,ServerInfo.ncClientLoanCmdTemplate);
		startLogTransfer(ServerInfo.NCfinanceClientIp, ServerInfo.ncfdfinanceLogDir,ServerInfo.ncClientFinanceCmdTemplate);
		startLogTransfer(ServerInfo.NCdockClientIp, ServerInfo.ncfddockLogDir,ServerInfo.ncClientDockCmdTemplate);*/


		new NcLogUtil() {

			@Override
			public void process() {
				//开始接口测试
				Response temp = given().param("phone", "18600001018").param("password", "123qwe").post("account/user/login");
				Response temp1 = given().param("money", Integer.valueOf("1001")).cookies(temp.getCookies())
						.post("funds/auth/rechargeMethod");
				SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,"scp -r /root/serverlog admin@192.168.16.185:/D:/abc/");


			}
		};



		//停掉server所有的nc监听端口
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncStopServerCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
				"scp -r /root/serverlog admin@192.168.16.185:/D:/abc/");
		System.out.println("over");
	}

}
