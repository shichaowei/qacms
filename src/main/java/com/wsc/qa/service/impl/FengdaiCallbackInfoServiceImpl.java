package com.wsc.qa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wsc.qa.dao.admin.FengdaiCallbackInfoDao;
import com.wsc.qa.meta.CallbackInfo;
import com.wsc.qa.service.FengdaiCallbakInfoService;

@Service
public class FengdaiCallbackInfoServiceImpl implements FengdaiCallbakInfoService{

	@Resource
	private FengdaiCallbackInfoDao fengdaiCallbackInfoDao;

	@Override
	public int addCallbackInfo(CallbackInfo callbackInfo) {
		return fengdaiCallbackInfoDao.addCallbackInfo(callbackInfo);
	}

	@Override
	public List<CallbackInfo> getAllCallbakInfo(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage,pageSize);
		return fengdaiCallbackInfoDao.getAllCallbakInfo();
	}

}
