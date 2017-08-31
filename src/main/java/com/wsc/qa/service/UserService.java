package com.wsc.qa.service;

import com.wsc.qa.meta.User;

public interface UserService {
	public User getUserInfo(String userName);
	
	public int insertUserInfo(User user);

}
