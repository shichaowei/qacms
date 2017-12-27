package com.wsc.qa.meta;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class MockInfo {

	int  id;
	@NotEmpty(message="ip不能为空")
	String mockserverip;
	@NotEmpty(message="mockType不能为空")
	String mockType;
	@NotEmpty(message="ContentType不能为空")
	String ContentType;
	@NotBlank(message="checkUrl不能为空")
	String checkUrl;

	String checkParams;
	String opername;
	MultipartFile  responseBodyFile;

	String responseBody;
	String mocktime;
	int delaytime;


	public MultipartFile getResponseBodyFile() {
		return responseBodyFile;
	}
	public void setResponseBodyFile(MultipartFile responseBodyFile) {
		this.responseBodyFile = responseBodyFile;
	}
	public int getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(int delaytime) {
		this.delaytime = delaytime;
	}
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

	@Override
	public String toString() {
		return "MockInfo [id=" + id + ", mockserverip=" + mockserverip + ", mockType=" + mockType + ", ContentType="
				+ ContentType + ", checkUrl=" + checkUrl + ", checkParams=" + checkParams + ", opername=" + opername
				+ ", responseBodyFile=" + responseBodyFile + ", responseBody=" + responseBody + ", mocktime=" + mocktime
				+ ", delaytime=" + delaytime + "]";
	}


}
