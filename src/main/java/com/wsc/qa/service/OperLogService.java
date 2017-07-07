package com.wsc.qa.service;

import com.wsc.qa.meta.OperaLog;

public interface OperLogService {
	public OperaLog getLastOper();
	
	public void insertOperLog(String username,String opertype);
	
}
