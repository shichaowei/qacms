package com.wsc.qa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.qa.dao.UserDao;
import com.wsc.qa.meta.User;
import com.wsc.qa.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	
	public User getUserInfo(String userName){
		return userDao.getUserInfo(userName);
	}

	@Override
	public int insertUserInfo(User user) {
		return userDao.insertUserInfo(user);
	}

	
}
