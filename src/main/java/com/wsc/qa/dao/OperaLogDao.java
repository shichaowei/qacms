package com.wsc.qa.dao;

import javax.annotation.Resource;

import com.wsc.qa.meta.OperaLog;
@Resource
public interface OperaLogDao {

//	@Select("select * FROM fengdaioperlog where status='SUCCESS' ORDER BY opertime DESC LIMIT 1")
	public OperaLog getLastOper();

	public OperaLog getLastOperByType(String opertype);

//	@Insert("INSERT into fengdaioperlog(username,opertype,opertime) VALUES (#{username},#{opertype},now())")
//	public void insertOper(@Param("username") String username,@Param("opertype") String opertype);

//	@Insert("INSERT into fengdaioperlog(username,opertype,opertime,status) VALUES (#{operLog.username},#{operLog.opertype},now(),#{operLog.status})")
	public int insertOper(OperaLog operlog);

//	@Update("UPDATE fengdaioperlog SET status=#{status} WHERE id=#{id}")
	public void updateStatus(OperaLog operaLog);


}
