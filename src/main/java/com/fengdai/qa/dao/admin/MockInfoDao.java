package com.fengdai.qa.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.DS;
import com.fengdai.qa.meta.MockInfo;

@DS(value=DataSourceConsts.DEFAULT)
@Mapper
public interface MockInfoDao {


	public int addMockinfo(MockInfo mockInfo);

	public List<MockInfo> getAllMockInfos();

	public void deleteMockinfoByid(int id);

	public void deleteAllMockinfo();

}
