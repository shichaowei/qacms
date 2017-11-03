package com.wsc.qa.mockmode;

import java.util.List;

public class ModeBeforeSendRequestBody {
	List<modecheckParamsStr> checkStrList;
	List<modeLocalResponse> localResList;
	public List<modecheckParamsStr> checkStrList() {
		return checkStrList;
	}
	public ModeBeforeSendRequestBody setCheckParamsStr(List<modecheckParamsStr> checkStrList) {
		this.checkStrList = checkStrList;
		return this;
	}
	public List<modeLocalResponse> getLocalResList() {
		return localResList;
	}
	public ModeBeforeSendRequestBody setLocalRes(List<modeLocalResponse> localResList) {
		this.localResList = localResList;
		return this;
	}

	private String getifblocks() {
		StringBuffer result = new StringBuffer();
		String template="if(%s){return{response:{%s}}}";
		for(int i=0;i<checkStrList.size();i++) {
			result.append(String.format(template, checkStrList.get(i),localResList.get(i)));
		}
		return result.toString();
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(String.format("*beforeSendRequest(requestDetail){%s},", getifblocks()));
		return result.toString();
	}



}
