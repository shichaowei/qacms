package com.wsc.qa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.qa.dao.OperaLogDao;
import com.wsc.qa.meta.OperaLog;
import com.wsc.qa.service.OperLogService;

@Service
public class OperLogServiceImpl implements OperLogService{
	@Resource
	private OperaLogDao operaLogDao;
	
	public OperaLog getLastOper(){
		return operaLogDao.getLastOper();
	}
//	@Deprecated
//	public void insertOperLog(String username,String opertype){
//		operaLogDao.insertOper(username, opertype);
//	}

	@Override
	public void insertOperLog(OperaLog operaLog) {
		operaLogDao.insertOper(operaLog);
	}
	
}
