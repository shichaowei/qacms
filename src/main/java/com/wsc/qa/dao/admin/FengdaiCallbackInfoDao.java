package com.wsc.qa.dao.admin;

import java.util.List;

import javax.annotation.Resource;

import com.wsc.qa.meta.CallbackInfo;

@Resource
public interface FengdaiCallbackInfoDao {


	public int addCallbackInfo(CallbackInfo callbackInfo);

	public List<CallbackInfo> getAllCallbakInfo();

}
