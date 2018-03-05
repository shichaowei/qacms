package com.fengdai.qa.service;

import com.fengdai.qa.meta.User;

public interface UserService {
	public User getUserInfo(String userName);
	
	public int insertUserInfo(User user);

}
