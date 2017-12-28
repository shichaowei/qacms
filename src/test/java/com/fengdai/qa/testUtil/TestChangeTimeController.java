package com.fengdai.qa.testUtil;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wsc.qa.web.controller.ServerChangeTimeController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-mvc.xml","classpath:application-context.xml"})
public class TestChangeTimeController {
	@Autowired
	ServerChangeTimeController serverChangeTimeController;



	MockMvc mockMvc;

	@Before
	public void setup(){
	    mockMvc = MockMvcBuilders.standaloneSetup(serverChangeTimeController).build();
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
	public void test2() throws Exception {

		ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.post("/newchangetime").param("changetimetype", "changeServicetime")
				.param("jobid", "1022").param("date", "2017/12/12").param("time", "15:03:00"));
		System.out.println(resultActions.andReturn().getResponse().getContentAsString());

	}

}
