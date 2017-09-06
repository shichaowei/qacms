package com.wsc.qa.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonFormatUtil
{
	
	public static String jsonFormatter(String uglyJSONString){
		System.out.println(uglyJSONString);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	
  

    public static void main(String[] args) {
        JsonFormatUtil json = new JsonFormatUtil();
//        String uglyJSONString = "[{\"amount\":1000.0,\"orderId\":\"6ab8f6264d344d52b07b2ec7c3645cc0\",\"status\":\"PAY_SUCCESS\",\"userParams\":\"{\\\"mcId\\\":\\\"b8d3b24f601f434ebc06ef83b967c815\\\",\\\"investor\\\":\\\"董贤海-15500000023\\\",\\\"actualLoanAmount\\\":\\\"1000.000000\\\"}\"},{\"amount\":10.0,\"orderId\":\"3aaeb3821ecc4752b225be33065f5a2d\",\"status\":\"PAY_SUCCESS\",\"userParams\":\"{\\\"mcId\\\":\\\"b8d3b24f601f434ebc06ef83b967c815\\\",\\\"investor\\\":\\\"董贤海-15500000023\\\",\\\"actualLoanAmount\\\":\\\"1000.000000\\\"}\"},{\"amount\":10.0,\"orderId\":\"0f58090adc5946d4926a52b78c9aeb78\",\"status\":\"PAY_SUCCESS\",\"userParams\":\"{\\\"mcId\\\":\\\"b8d3b24f601f434ebc06ef83b967c815\\\",\\\"investor\\\":\\\"董贤海-15500000023\\\",\\\"actualLoanAmount\\\":\\\"1000.000000\\\"}\"},{\"amount\":1000.0,\"status\":\"PAY_SUCCESS\"}]";
        String uglyJSONString = "module.exports={*beforeSendRequest(requestDetail){if(requestDetail.url.indexOf('http://172.30.251.176/credit-thirdparty-rest/api/auto/fill/factor')!=-1&&requestDetail.requestData.toString('utf-8').indexOf('魏士超')!=-1){return{response:{statusCode:200,header:{'Content-Type':'text/xml',Connection:'null','Cache-Control':'null'},body:'{{     \"code\": \"10100\",     \"data\": [         {             \"code\": \"realname_user\",             \"field_id\": \"\",             \"remark\": \"\",             \"type\": \"input\",             \"value\": \"魏士超\"         },         {             \"code\": \"unmber_idcard\",             \"field_id\": \"\",             \"remark\": \"\",             \"type\": \"input\",             \"value\": \"410581199007129054\"         },         {             \"code\": \"number_cellphone\",             \"field_id\": \"\",             \"remark\": \"\",             \"type\": \"input\",             \"value\": \"18667906998\"         },         {             \"code\": \"anti_fraud_score\",             \"field_id\": \"2065\",             \"remark\": \"\",             \"type\": \"input\",             \"value\": 10         },         {             \"code\": \"personal_information_score\",             \"field_id\": \"2066\",             \"remark\": \"\",             \"type\": \"input\",             \"value\": 35         },         {             \"code\": \"job_score\",             \"field_id\": \"2067\",             \"remark\": \"\",   }'}}}},}";
        String prettyJsonString = SmilarJSONFormatUtil.format(uglyJSONString.replace(" ", ""));
		System.out.println("JSON格式化前：");
		System.out.println(uglyJSONString);
		System.out.println("JSON格式化后：");
		System.out.println(prettyJsonString);

    }
}