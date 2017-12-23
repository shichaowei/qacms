package com.wsc.qa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsc.qa.dao.fengdaiold.FengdaiOldDao;
import com.wsc.qa.service.FengdaiDbOldService;

@Service
public class FengdaiDbOldServiceImpl implements FengdaiDbOldService{

	@Autowired
	private FengdaiOldDao fengdaiDao;

	@Override
	public String getremark(String relateid) {
		return fengdaiDao.getremarkViaRelate(relateid);
	}

	@Override
	public String getremarkNew(String relateid) {
		return fengdaiDao.getremarkViaRelateNew(relateid);
	}

}
