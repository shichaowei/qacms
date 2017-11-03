package com.wsc.qa.dao;

import java.util.List;

import javax.annotation.Resource;

import com.wsc.qa.meta.MockInfo;

@Resource
public interface MockInfoDao {


	public int addMockinfo(MockInfo mockInfo);

	public List<MockInfo> getAllMockInfos();

	public void deleteMockinfoByid(int id);

	public void deleteAllMockinfo();

}
