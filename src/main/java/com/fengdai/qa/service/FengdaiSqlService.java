package com.fengdai.qa.service;


import java.util.Date;

import com.fengdai.qa.meta.FDSqlInfo;

public interface FengdaiSqlService {

	public int addSql(FDSqlInfo fdSqlInfo);

	public Date getDbtime();

}
