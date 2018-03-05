package com.fengdai.qa.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fengdai.qa.annotation.DS;
import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.meta.OperaLog;

@DS(value=DataSourceConsts.DEFAULT)
@Mapper
public interface OperaLogMapper {

//	@Select("select * FROM fengdaioperlog where status='SUCCESS' ORDER BY opertime DESC LIMIT 1")
	public OperaLog getLastOper();

//	@Select("select * from fengdaioperlog where status='SUCCESS' and opertype=#{opertype} ORDER BY opertime DESC LIMIT 1")
	public OperaLog getLastOperByType(String opertype);

//	@Insert("INSERT into fengdaioperlog(username,opertype,opertime) VALUES (#{username},#{opertype},now())")
	public void insertOper(@Param("username") String username,@Param("opertype") String opertype);

//	@Insert("INSERT into fengdaioperlog(username,opertype,opertime,status) VALUES (#{operLog.username},#{operLog.opertype},now(),#{operLog.status})")
	public int insertOper(OperaLog operlog);

//	@Update("UPDATE fengdaioperlog SET status=#{status} WHERE id=#{id}")
	public void updateStatus(OperaLog operaLog);


}
