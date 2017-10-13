package com.wsc.qa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsc.qa.dao.FengdaiDao;
import com.wsc.qa.service.FengdaiService;

@Service
public class FengdaiServiceImpl implements FengdaiService {

	@Autowired
	private FengdaiDao fengdaiDao;

	@Override
	public String getremark(String relateid) {
		return fengdaiDao.getremarkViaRelate(relateid);
	}

}
