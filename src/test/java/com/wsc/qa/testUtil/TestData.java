package com.wsc.qa.testUtil;

import static io.restassured.RestAssured.given;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestData {

	public static void main(String[] args) {
	    String targetStr = "最近5年内有7个月处于逾期状态";
	      String regEx = "最近5年内有([1-9]\\d*|0)个月处于逾期状态";
	      Pattern pattern = Pattern.compile(regEx);
	      Matcher matcher = pattern.matcher(targetStr);
	      boolean rs = matcher.matches();
	      System.out.println(rs);

	      String regEx2 = ".*([1-9]\\d*|0).*";
	      Pattern pattern2 = Pattern.compile(regEx2);
	      System.out.println(pattern2.matcher("wsc0wscdfd").matches());

	      String var = given().auth().preemptive().basic("hzweisc", "111111").when().
	      get("http://10.200.130.105:8080/job/蜂贷3.0/view/01_编译_打包-spring-test/job/fengdai-common/build?token=fengdai-common").getBody().asString();
	      System.out.println(var);
	  }
}
