package com.wsc.qa.testUtil;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.UnsupportedEncodingException;

import io.restassured.response.Response;

public class TestGiven {



	public static void main(String[] args) throws UnsupportedEncodingException {

		Response response = given().multiPart("fileName",new File("C:\\Users\\hzweisc\\Pictures\\2.6\\1.png")).post("http://127.0.0.1:8080/api/caseupload").andReturn();
		System.out.println(response.asString());

//		Response temp = given().param("userName", "fengdai").param("password", "fengdai2017")
//				.param("ifRemember", "on").post("http://10.200.141.52:8081/schedule-admin/login");
//		Response temp1 = given().param("id", Integer.valueOf("1022")).cookies(temp.getCookies()).post("http://10.200.141.52:8081/schedule-admin/jobinfo/trigger");
//		System.out.println(temp1.asString());

//		new LogPrepareUtil() {
//
//			@Override
//			public void process() {
//				// TODO Auto-generated method stub
//
//				Response response = given().cookie("token","D6960D67C79943F4B9879DCB1B3CAEDE.A9195A65CE84861E3168B0ED9470FA74").
//						get("http://10.200.141.72/ReportWeb/riskcontrol_urge/entrust_order_list?pageNum=1&pageSize=10");
//				System.out.println(response.asString());
//			}
//		};
//		String dingdingbody="{\"msgtype\":\"text\",\"text\":{\"content\":\"修改时间:%s\"}}";
////		String body = new String(.getBytes(),"utf-8");
//		Response response = given().cookie("token","D6960D67C79943F4B9879DCB1B3CAEDE.A9195A65CE84861E3168B0ED9470FA74").
//				get("http://10.200.141.65:92/ReportWeb/riskcontrol_urge/entrust_order_list?pageNum=1&pageSize=10");
//		System.out.println(response.asString());
//		long endTime=System.currentTimeMillis();

	}

}
