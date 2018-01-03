package com.wsc.qa.dao.admin;

import java.util.List;

import com.wsc.qa.meta.CallbackInfo;

public interface FengdaiCallbackInfoDao {


	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo();

}
