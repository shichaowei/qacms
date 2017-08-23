package com.wsc.qa.dao;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Select;

import com.wsc.qa.meta.User;
@Resource
public interface UserDao {
	
	
	
	@Select("select * from fengdaiuser where userName=#{userName}")
	public User getUserInfo(String userName);
}
