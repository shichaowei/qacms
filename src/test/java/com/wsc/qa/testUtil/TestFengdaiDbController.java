package com.wsc.qa.testUtil;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fengdai.qa.service.FengdaiDbNewUserInfoService;
import com.fengdai.qa.web.controller.FengdaiDbController;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFengdaiDbController {
	@Autowired
	FengdaiDbController fengdaiDbController;

	@Autowired
	FengdaiDbNewUserInfoService fengdaiUserInfoServiceImpl;

	@Autowired
    private WebApplicationContext wac;

	MockMvc mockMvc;

	@Before
	public void setup(){
//	    mockMvc = MockMvcBuilders.standaloneSetup(new FengdaiDbController()).build();
	  //MockMvcBuilders使用构建MockMvc对象   （项目拦截器有效）
	    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	    //单个类  拦截器无效
	  // mockMvc = MockMvcBuilders.standaloneSteup(userController).build();
	}

//	@Test
//	public void test1() throws Exception {
//		MultiValueMap<String, String> var = new LinkedMultiValueMap<>();
//		var.add("deleteMode", "NEW");
//		var.add("deleteType", "deleteUserByLoginname");
//		var.add("param", "18667918000");
//		var.add("moneynumStr", "1000");
//		ResultActions resultActions =  this.mockMvc.perform(MockMvcRequestBuilders.post("/deleteUserInfo").params(var));
//		MvcResult mvcResult = resultActions.andReturn();
//        String result = mvcResult.getResponse().getContentAsString();
//        System.out.println("=====客户端获得反馈数据:" + result);
//	}

	@Test
	public void test2() throws InterruptedException, ExecutionException {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		for (int index = 3061; index < 8000; index++) {
			final int i = index;
			fixedThreadPool.submit(new Runnable() {

				@Override
				public void run() {
					System.out.println(i);
					String loginname = "1866791" + String.valueOf(i);
					MultiValueMap<String, String> var = new LinkedMultiValueMap<>();
					var.add("deleteMode", "NEW");
					var.add("deleteType", "deleteUserByLoginname");
					var.add("param", loginname);
					var.add("moneynumStr", "1000");
					try {
						ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.post("/api/deleteUserInfo").params(var));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).get();
		}
		fixedThreadPool.shutdown();
		while(true) {
			if(fixedThreadPool.isTerminated()) {
				break;
			}
		}

	}

}
