package com.fengdai.qa.dao.admin;

import java.util.List;

import com.fengdai.qa.meta.MockInfo;

public interface MockInfoDao {


	public int addMockinfo(MockInfo mockInfo);

	public List<MockInfo> getAllMockInfos();

	public void deleteMockinfoByid(int id);

	public void deleteAllMockinfo();

}
