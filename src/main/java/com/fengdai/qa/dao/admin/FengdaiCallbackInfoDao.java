package com.fengdai.qa.dao.admin;

import java.util.List;

import com.fengdai.qa.meta.CallbackInfo;

public interface FengdaiCallbackInfoDao {


	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo();

}
