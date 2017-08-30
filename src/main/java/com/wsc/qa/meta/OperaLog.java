package com.wsc.qa.meta;

import com.wsc.qa.constraint.NotBlank;

public class OperaLog {

	@NotBlank
	String username;
	@NotBlank
	String opertype;
	@NotBlank
	String opertime;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOpertype() {
		return opertype;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public String getOpertime() {
		return opertime;
	}
	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}
	
}
