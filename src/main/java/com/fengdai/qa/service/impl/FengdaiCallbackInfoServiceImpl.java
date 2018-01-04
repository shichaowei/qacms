package com.fengdai.qa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fengdai.qa.dao.admin.FengdaiCallbackInfoDao;
import com.fengdai.qa.meta.CallbackInfo;
import com.fengdai.qa.service.FengdaiCallbakInfoService;
import com.github.pagehelper.PageHelper;

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
