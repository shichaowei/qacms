package com.wsc.qa.meta;

import org.springframework.web.multipart.MultipartFile;

import com.wsc.qa.constraint.NotBlank;

public class MockInfo {

	int  id;
	@NotBlank
	String mockserverip;
	@NotBlank
	String mockType;
	@NotBlank
	String ContentType;
	@NotBlank
	String checkUrl;

	String checkParams;
	String opername;
	MultipartFile  responseBodyFile;

	String responseBody;
	String mocktime;


	public String getOpername() {
		return opername;
	}
	public void setOpername(String opername) {
		this.opername = opername;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMocktime() {
		return mocktime;
	}
	public void setMocktime(String mocktime) {
		this.mocktime = mocktime;
	}
	public MultipartFile getCheckParamsFile() {
		return responseBodyFile;
	}
	public void setCheckParamsFile(MultipartFile responseBodyFile) {
		this.responseBodyFile = responseBodyFile;
	}


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
