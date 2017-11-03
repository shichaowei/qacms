package com.wsc.qa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.dao.MockInfoDao;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.mockmode.ModeBeforeSendRequestBody;
import com.wsc.qa.mockmode.modeExports;
import com.wsc.qa.mockmode.modeLocalResponse;
import com.wsc.qa.mockmode.modecheckParamsStr;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.utils.LogUtil;
import com.wsc.qa.utils.Scpclient;
import com.wsc.qa.utils.SshUtil;
import com.wsc.qa.utils.WriteFileUtil;

@Service
public class MockMessServiceImpl implements MockMessService{

	private final LogUtil logger = new LogUtil(this.getClass());

	@Autowired
	private MockInfoDao mockInfoDao;
	/**
	 * 1.构造localResponse
	 * 2.构造查询条件 modecheckParamsStr
	 * 3.构造beforeSendRequestBody代码段
	 * 4.构造modeExports
	 *
	 */
	@Override
	public String mockProcess(List<MockInfo> mockInfoList) {

		List<modecheckParamsStr> checkStrList = new ArrayList<>();
		List<modeLocalResponse> localResList = new ArrayList<>();
		for(MockInfo mockInfo:mockInfoList) {
			String mockServerIp = mockInfo.getMockserverip();
			String ContentType = mockInfo.getContentType();
			String responseBody = mockInfo.getResponseBody();
			String checkUrl = mockInfo.getCheckUrl();

			List<String> checkPostParams = null;
			List<String> checkGetParams = null;
			switch (mockInfo.getMockType()) {
			case "get":
				if(mockInfo.getCheckParams()!=null) {
					checkGetParams = java.util.Arrays.asList(mockInfo.getCheckParams().split(";"));
				}else {
					checkGetParams=null;
				}
				break;
			case "post":
				if (mockInfo.getCheckParams()!=null) {
					checkPostParams = java.util.Arrays.asList(mockInfo.getCheckParams().split(";"));
				}else {
					checkPostParams=null;
				}
				break;
			default:
				break;
			}

			modeLocalResponse localResponse = new modeLocalResponse();
			modeLocalResponse.headerDetail headers = localResponse.new headerDetail();
			headers.setContentType(ContentType);
			localResponse.setStatusCode(200).setHeader(headers).setBody(responseBody);
			localResList.add(localResponse);

			modecheckParamsStr modecheckParamsStr = new modecheckParamsStr();
			modecheckParamsStr.setCheckUrl(checkUrl);
			modecheckParamsStr.setCheckPostParams(checkPostParams);
			modecheckParamsStr.setCheckGetParams(checkGetParams);
			checkStrList.add(modecheckParamsStr);
		}
		modeExports modeExports;
    	ModeBeforeSendRequestBody beforeSendRequestBody = new ModeBeforeSendRequestBody();
		beforeSendRequestBody.setCheckParamsStr(checkStrList).setLocalRes(localResList);
		modeExports = new modeExports();
		modeExports.setBeforeSendRequestBody(beforeSendRequestBody);
		String filename = "rule" + System.currentTimeMillis() + ".txt";
		WriteFileUtil.clearWriteFile(modeExports.toString(), filename);
		Scpclient scp = Scpclient.getInstance(ServerInfo.mockServerIp, 22, ServerInfo.sshname, ServerInfo.sshpwd);
		scp.putFile(filename, filename, ServerInfo.anyproxyRulePath, null);
		logger.logInfo(filename);
		SshUtil.remoteRunCmd(ServerInfo.mockServerIp, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ServerInfo.restartanyproxyShellMode, filename), false);
		return modeExports.toString();



//        if (!mockInfo.getMockType().equals("reset")) {
//        	modeExports modeExports;
//
//        	ModeBeforeSendRequestBody beforeSendRequestBody = new ModeBeforeSendRequestBody();
//			beforeSendRequestBody.setCheckParamsStr(modecheckParamsStr).setLocalRes(localResponse);
//			modeExports = new modeExports();
//			modeExports.setBeforeSendRequestBody(beforeSendRequestBody);
//			String filename = "rule" + System.currentTimeMillis() + ".txt";
//			WriteFileUtil.clearWriteFile(modeExports.toString(), filename);
//			Scpclient scp = Scpclient.getInstance(mockServerIp, 22, ServerInfo.sshname, ServerInfo.sshpwd);
//			scp.putFile(filename, filename, ServerInfo.anyproxyRulePath, null);
//			logger.logInfo(filename);
//			SshUtil.remoteRunCmd(mockServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
//					String.format(ServerInfo.restartanyproxyShellMode, filename), false);
//			return modeExports.toString();
//		}else if(mockInfo.getMockType().equals("reset")){
//			SshUtil.remoteRunCmd(mockServerIp, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ServerInfo.restartanyproxyShellMode, CommonConstants.ShellInfo.restanyproxyrule.getValue()), false);
//			return "{\"mocktype\":\"重置anyproxy的规则\"}";
//		}else {
//			throw new BusinessException(ErrorCode.ERROR_ILLEGAL_PARAMTER);
//		}



	}
	@Override
	public int addMockinfo(MockInfo mockInfo) {
		return mockInfoDao.addMockinfo(mockInfo);
	}
	@Override
	public List<MockInfo> getAllMockInfos() {

		return mockInfoDao.getAllMockInfos();
	}
	@Override
	public void deleteMockinfoByid(int id) {
		mockInfoDao.deleteMockinfoByid(id);

	}
	@Override
	public void deleteAllMockinfo() {

		mockInfoDao.deleteAllMockinfo();
	}

}
