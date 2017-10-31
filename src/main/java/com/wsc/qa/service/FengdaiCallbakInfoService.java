package com.wsc.qa.service;

import java.util.List;

import com.wsc.qa.meta.CallbackInfo;

public interface FengdaiCallbakInfoService {

	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo(int currentPage, int pageSize);
}
