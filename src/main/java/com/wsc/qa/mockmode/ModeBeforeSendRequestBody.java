package com.wsc.qa.mockmode;

public class ModeBeforeSendRequestBody {
	modecheckParamsStr checkStr;
	modeLocalResponse localRes;
	public modecheckParamsStr getCheckStr() {
		return checkStr;
	}
	public ModeBeforeSendRequestBody setCheckParamsStr(modecheckParamsStr checkStr) {
		this.checkStr = checkStr;
		return this;
	}
	public modeLocalResponse getLocalRes() {
		return localRes;
	}
	public ModeBeforeSendRequestBody setLocalRes(modeLocalResponse localRes) {
		this.localRes = localRes;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(String.format("*beforeSendRequest(requestDetail){if(%s){return{response:{%s}}}},", checkStr.toString(),localRes.toString()));
		
		return result.toString();
	}
	
	

}
