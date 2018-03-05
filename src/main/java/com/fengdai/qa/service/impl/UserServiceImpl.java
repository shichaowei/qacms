package com.fengdai.qa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fengdai.qa.dao.admin.UserDao;
import com.fengdai.qa.meta.User;
import com.fengdai.qa.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;

	@Override
	public User getUserInfo(String userName){
		return userDao.getUserInfo(userName);
	}

	@Override
	public int insertUserInfo(User user) {
		return userDao.insertUserInfo(user);
	}


}
