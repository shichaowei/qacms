package com.fengdai.qa.service;

import java.util.List;

import com.fengdai.qa.meta.CallbackInfo;

public interface FengdaiCallbakInfoService {

	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo(int currentPage, int pageSize);
}
