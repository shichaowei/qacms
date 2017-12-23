package com.wsc.qa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsc.qa.dao.fengdainew.FengdaiDao;
import com.wsc.qa.service.FengdaiDbNewService;

@Service
public class FengdaiDbNewServiceImpl implements FengdaiDbNewService {

	@Autowired
	private FengdaiDao fengdaiDao;

	@Override
	public String getremark(String relateid) {
		return fengdaiDao.getremarkViaRelate(relateid);
	}

	@Override
	public String getremarkNew(String relateid) {
		return fengdaiDao.getremarkViaRelateNew(relateid);
	}

}
