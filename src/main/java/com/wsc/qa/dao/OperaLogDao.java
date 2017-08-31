package com.wsc.qa.dao;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsc.qa.meta.OperaLog;
@Resource
public interface OperaLogDao {

	@Select("select * FROM fengdaioperlog ORDER BY opertime DESC LIMIT 1")
	public OperaLog getLastOper();
	
//	@Insert("INSERT into fengdaioperlog(username,opertype,opertime) VALUES (#{username},#{opertype},now())")
//	public void insertOper(@Param("username") String username,@Param("opertype") String opertype);
	
	@Insert("INSERT into fengdaioperlog(username,opertype,opertime) VALUES (#{operLog.username},#{operLog.opertype},now())")
	public void insertOper(@Param("operLog") OperaLog operlog);
	
	
}
