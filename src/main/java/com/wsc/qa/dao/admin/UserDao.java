package com.wsc.qa.dao.admin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wsc.qa.meta.User;
public interface UserDao {

	@Insert("INSERT into fengdaiuser(userName,userPassword) VALUES (#{user.userName},#{user.userPassword})")
	public int insertUserInfo(@Param("user") User user);

	@Select("select * from fengdaiuser where userName=#{userName}")
	public User getUserInfo(String userName);
}
