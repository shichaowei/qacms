package com.wsc.qa.dao;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsc.qa.meta.User;
@Resource
public interface UserDao {
	
	@Insert("INSERT into fengdaiuser(userName,userPassword) VALUES (#{user.userName},#{user.userPassword})")
	public int insertUserInfo(@Param("user") User user);
	
	@Select("select * from fengdaiuser where userName=#{userName}")
	public User getUserInfo(String userName);
}
