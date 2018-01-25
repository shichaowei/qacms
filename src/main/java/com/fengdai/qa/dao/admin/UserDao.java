package com.fengdai.qa.dao.admin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.DS;
import com.fengdai.qa.meta.User;
@DS(value=DataSourceConsts.DEFAULT)
@Mapper
public interface UserDao {

	@Insert("INSERT into fengdaiuser(userName,userPassword) VALUES (#{user.userName},#{user.userPassword})")
	public int insertUserInfo(@Param("user") User user);

	@Select("select * from fengdaiuser where userName=#{userName}")
	public User getUserInfo(String userName);
}
