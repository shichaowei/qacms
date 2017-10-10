package com.wsc.qa.meta;

import com.wsc.qa.constraint.NotBlank;

public class User {

	@NotBlank
	String userName ;

	@NotBlank
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
