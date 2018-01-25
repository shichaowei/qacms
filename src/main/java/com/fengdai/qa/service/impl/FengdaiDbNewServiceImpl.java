package com.fengdai.qa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.DS;
import com.fengdai.qa.dao.fengdainew.FengdaiDao;
import com.fengdai.qa.service.FengdaiDbNewService;

@Service
@DS(value=DataSourceConsts.fengdai3)
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
