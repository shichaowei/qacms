package com.wsc.qa.meta;

import com.wsc.qa.constraint.NotBlank;

public class MockInfo {
	
	@NotBlank
	String mockserverip;
	@NotBlank
	String mockType;
	@NotBlank
	String ContentType;
	@NotBlank
	String checkUrl;
	String checkParams;
	@NotBlank
	String responseBody;
	public String getMockserverip() {
		return mockserverip;
	}
	public void setMockserverip(String mockserverip) {
		this.mockserverip = mockserverip;
	}
	public String getMockType() {
		return mockType;
	}
	public void setMockType(String mockType) {
		this.mockType = mockType;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getCheckUrl() {
		return checkUrl;
	}
	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}
	public String getCheckParams() {
		return checkParams;
	}
	public void setCheckParams(String checkParams) {
		this.checkParams = checkParams;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	

}
