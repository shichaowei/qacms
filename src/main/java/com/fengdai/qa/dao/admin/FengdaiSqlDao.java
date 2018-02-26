package com.fengdai.qa.dao.admin;


import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.fengdai.qa.annotation.DS;
import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.meta.FDSqlInfo;


@DS(value=DataSourceConsts.DEFAULT)
@Mapper
public interface FengdaiSqlDao {

	@Insert("INSERT INTO fengdaisqls(sqlcontent,addtime) VALUES(#{sql}, now())")
	public int addSql(FDSqlInfo fdSqlInfo);

	@Select("SELECT NOW()")
	public Date getDbtime();


}
