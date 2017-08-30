package com.wsc.qa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.mockmode.*;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.utils.Scpclient;
import com.wsc.qa.utils.SshUtil;
import com.wsc.qa.utils.WriteToFile;

@Service
public class MockMessServiceImpl implements MockMessService{

	/**
	 * 1.构造localResponse
	 * 2.构造查询条件 modecheckParamsStr
	 * 3.构造beforeSendRequestBody代码段
	 * 4.构造modeExports
	 * 
	 */
	public String mockProcess(MockInfo mockInfo) {
		String mockServerIp=mockInfo.getMockserverip();
		String ContentType=mockInfo.getContentType();
		String responseBody=mockInfo.getResponseBody();
		String checkUrl=mockInfo.getCheckUrl();
		List<String> checkPostParams =null;
		List<String> checkGetParams = null;
		switch (mockInfo.getMockType()) {
		case "get":
			checkGetParams= java.util.Arrays.asList( mockInfo.getCheckParams().split(";"));
			break;
		case "post":
			checkPostParams= java.util.Arrays.asList( mockInfo.getCheckParams().split(";"));
			break;
		default:
			break;
		}
		
		modeLocalResponse localResponse = new modeLocalResponse();
    	modeLocalResponse.headerDetail headers= localResponse.new headerDetail();
    	headers.setContentType(ContentType);
    	localResponse.setStatusCode(200).setHeader(headers).setBody(responseBody);
    	
        modecheckParamsStr modecheckParamsStr = new modecheckParamsStr();
        modecheckParamsStr.setCheckUrl(checkUrl);
        modecheckParamsStr.setCheckPostParams(checkPostParams);
        modecheckParamsStr.setCheckGetParams(checkGetParams);
    	
        modeBeforeSendRequestBody beforeSendRequestBody = new modeBeforeSendRequestBody();
        beforeSendRequestBody.setCheckParamsStr(modecheckParamsStr).setLocalRes(localResponse);
        
        modeExports modeExports = new modeExports();
        modeExports.setBeforeSendRequestBody(beforeSendRequestBody);
        String filename = "rule"+new Date().getTime()+".txt";
        WriteToFile.clearWriteFile(modeExports.toString(), filename);
        Scpclient scp = Scpclient.getInstance(mockServerIp, 22,ServerInfo.sshname,ServerInfo.sshpwd);
        scp.putFile(filename, filename, ServerInfo.anyproxyRulePath, null);
        System.out.println(filename);
        SshUtil.remoteRunCmd(mockServerIp,ServerInfo.sshname,ServerInfo.sshpwd, String.format(ServerInfo.restartanyproxyShellMode,filename),false);
        return modeExports.toString();
        
	}
	
}
