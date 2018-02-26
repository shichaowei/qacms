package com.fengdai.qa.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengdai.qa.dao.admin.FengdaiSqlDao;
import com.fengdai.qa.meta.FDSqlInfo;
import com.fengdai.qa.service.FengdaiSqlService;

@Service
public class FengdaiSqlServiceImpl implements FengdaiSqlService {


	@Autowired
	private FengdaiSqlDao fengdaiSqlDao;

	@Override
	public int addSql(FDSqlInfo fdSqlInfo) {
		return fengdaiSqlDao.addSql(fdSqlInfo);
	}

	@Override
	public Date getDbtime() {
		return fengdaiSqlDao.getDbtime();
	}

}
