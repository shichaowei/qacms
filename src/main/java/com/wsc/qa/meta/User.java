package com.wsc.qa.meta;

import org.hibernate.validator.constraints.NotBlank;

public class User {
	@NotBlank(message="username不能为空")
	String userName ;
	@NotBlank(message="usrPasswd不能为空")
	String userPassword ;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


}
