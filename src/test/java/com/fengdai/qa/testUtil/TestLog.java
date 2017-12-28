package com.fengdai.qa.testUtil;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.utils.SshUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestLog {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "http://10.200.141.56/";
		Response temp = given().param("phone", "18600001018").param("password", "123qwe")
				.post("account/user/login");
//		SshWatched fundServerSshWatched =new SshWatched(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFundCmd);
//		SshWatcher fundServerSshWatcher = new SshWatcher(fundServerSshWatched);
//		SshWatched fundClientSshWatched =new SshWatched(ServerInfo.NCfundClientIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncClientFundCmd);
//		SshWatcher fundClientSshWatcher = new SshWatcher(fundClientSshWatched);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFundCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCfundClientIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncClientFundCmd);

		Response temp1 = given().param("money", Integer.valueOf("1001")).cookies(temp.getCookies())
				.post("funds/auth/rechargeMethod");
//		fundServerSshWatched.closesession();
//		fundClientSshWatched.closesession();
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncStopServerFundCmd);
		SshUtil.remoteRunCmd(ServerInfo.NCServerIp,ServerInfo.sshname, ServerInfo.sshpwd,"scp -r /root/serverlog admin@192.168.16.185:/D:/abc/");

	}

}
