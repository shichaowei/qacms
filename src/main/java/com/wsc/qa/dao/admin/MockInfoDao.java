package com.wsc.qa.dao.admin;

import java.util.List;

import com.wsc.qa.meta.MockInfo;

public interface MockInfoDao {


	public int addMockinfo(MockInfo mockInfo);

	public List<MockInfo> getAllMockInfos();

	public void deleteMockinfoByid(int id);

	public void deleteAllMockinfo();

}
