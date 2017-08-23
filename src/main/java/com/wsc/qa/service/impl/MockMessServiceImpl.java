package com.wsc.qa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengdai.qa.util.WriteToFile;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.mockmode.*;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.utils.Scpclient;
import com.wsc.qa.utils.SshUtil;

@Service
public class MockMessServiceImpl implements MockMessService{

	/**
	 * 1.构造localResponse
	 * 2.构造查询条件 modecheckParamsStr
	 * 3.构造beforeSendRequestBody代码段
	 * 4.构造modeExports
	 * 
	 */
	public String mockProcess(String mockServerIp,String ContentType,String responseBody,String checkUrl,List<String> checkPostParams,List<String> checkGetParams) {
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
