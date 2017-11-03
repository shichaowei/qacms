package com.wsc.qa.service;


import java.util.List;

import com.wsc.qa.meta.MockInfo;

public interface MockMessService {

	public String mockProcess(List<MockInfo> mockInfoList);

	public int addMockinfo(MockInfo mockInfo);

	public List<MockInfo> getAllMockInfos();

	public void deleteMockinfoByid(int id);

	public void deleteAllMockinfo();

}
