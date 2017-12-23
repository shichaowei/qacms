package com.wsc.qa.service;

import com.wsc.qa.meta.OperaLog;

public interface OperLogService {
	public OperaLog getLastOper();

	public OperaLog getLastOperByType(String opertype);
//	@Deprecated
//	public void insertOperLog(String username,String opertype);

	public int insertOperLog(OperaLog operaLog);

	public void updateOperLogStatus(OperaLog operaLog);

}
