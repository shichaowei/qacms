package com.wsc.qa.service;


import java.util.ArrayList;
import java.util.List;

public interface MockMessService {

	public String mockProcess(String mockServerIp,String ContentType,String responseBody,String checkUrl,java.util.List<String> checkPostParams,List<String> checkGetParams);
}
