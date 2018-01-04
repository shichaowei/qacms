package com.fengdai.qa.meta;

public class GetRequestResult {

	String requestType;
	String requestUrl;
	String requestContentType;
	String requestBody;
	String token;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getRequestContentType() {
		return requestContentType;
	}
	public void setRequestContentType(String requestContentType) {
		this.requestContentType = requestContentType;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}




}
