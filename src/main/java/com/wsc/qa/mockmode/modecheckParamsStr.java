package com.wsc.qa.mockmode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class modecheckParamsStr {
	
	private String checkUrl;
	private List<String> checkPostParams;
	private List<String> checkGetParams;
	public String getCheckUrl() {
		return checkUrl;
	}
	public modecheckParamsStr setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
		return this;
	}
	public List<String> getCheckPostParams() {
		return checkPostParams;
	}
	public modecheckParamsStr setCheckPostParams(List<String> checkPostParams) {
		this.checkPostParams = checkPostParams;
		return this;
	}
	public List<String> getCheckGetParams() {
		return checkGetParams;
	}
	public modecheckParamsStr setCheckGetParams(List<String> checkGetParams) {
		this.checkGetParams = checkGetParams;
		return this;
	}
	@Override
	public String toString() {
		String result="";
		StringBuffer checkSumString = new StringBuffer();
		
		if(!checkUrl.isEmpty()) {
			checkSumString.append(String.format("requestDetail.url.indexOf('%s') != -1", checkUrl));
		}
		if(null != checkPostParams && !checkPostParams.isEmpty()) {
			for(String var:checkPostParams) {
				checkSumString.append("&&"+String.format("requestDetail.requestData.toString('utf-8').indexOf('%s') != -1",var));
			}
		}
		if(null != checkGetParams && !checkGetParams.isEmpty()) {
			for(String var:checkGetParams) {
				checkSumString.append("&&"+String.format("requestDetail.url.indexOf('%s') != -1",var));
			}
		}
		
		String pattern = "^&&.*";
		if(Pattern.matches(pattern,checkSumString )){
			result = checkSumString.toString().replaceFirst("&&", "");
		}else {
			result=checkSumString.toString();
		}
			
		return result;
	}
	
	
	

}
