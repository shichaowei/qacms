package com.wsc.qa.utils;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.exception.BusinessException;


/**
 * @author hushengen  @date 2016年5月25日
 */
public class OkHttpUtil {

    public static final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    static {
        client.setConnectTimeout(240, TimeUnit.SECONDS);
        client.setReadTimeout(240, TimeUnit.SECONDS);
    }

    /**
     * http  get 方法
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException(response.body().string());
        }
    }

    public static String getHostParseRetry(String url) throws IOException {
    	int count = 1;
        Request request = new Request.Builder().url(url).build();
        String result = null;
        do {
			Response response = client.newCall(request).execute();
			result = response.body().string();
			if(response.isSuccessful()){
				break;
			}else if (result.contains("unknown")) {
				count++;
			}else{
				throw new IOException(result);
			}
		} while (count <= 3);
        return result;
    }


    /**
     * http  get获取字节 方法
     *
     * @param url
     * @param token
     * @return byte
     * @throws IOException
     */
    public static byte[] getbyte(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else {
            throw new IOException(response.body().string());
        }
    }


    /**
     * http  get 方法
     *
     * @param url
     * @param token
     * @return
     * @throws IOException
     */
    public static String getWithCookie(String url, String token) throws IOException {
        String tokenStr = "token=" + token;
        Request request = new Request.Builder().header("Cookie", tokenStr).url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException(response.body().string());
        }
    }

    /**
     * http post方法   application/json
     *
     * @param url
     * @param json json字符串参数
     * @return
     * @throws IOException
     */
    public static String post(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(APPLICATION_JSON, json == null ? "" : json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * http post方法   application/json
     *
     * @param url
     * @param json json字符串参数
     * @return
     * @throws IOException
     */
    public static String postWithCookies(String url, String json, String token) throws IOException {
        String tokenStr = "token=" + token;
        RequestBody requestBody = RequestBody.create(APPLICATION_JSON, json == null ? "" : json);
        Request request = new Request.Builder().header("Cookie", tokenStr).url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public static Response postResponse(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(APPLICATION_JSON, json == null ? "" : json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 表单post提交   application/x-www-form-urlencoded
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static String postForm(String url, final Map<String, Object> paramsMap) throws IOException {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                formEncodingBuilder.add(key, String.valueOf(paramsMap.get(key)));
            }
        }
        Request request = new Request.Builder().url(url).post(formEncodingBuilder.build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException(response.body().string());
        }
    }

    public static String postFormWithCookie(String url, final Map<String, Object> paramsMap,String token) throws IOException {

    	String tokenStr = "token=" + token;
    	FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
    	if (paramsMap != null) {
    		for (String key : paramsMap.keySet()) {
    			formEncodingBuilder.add(key, String.valueOf(paramsMap.get(key)));
    		}
    	}
    	Request request = new Request.Builder().header("Cookie", tokenStr).url(url).post(formEncodingBuilder.build()).build();
    	Response response = client.newCall(request).execute();
    	if (response.isSuccessful()) {
    		return response.body().string();
    	} else {
    		throw new IOException(response.body().string());
    	}
    }

    /**
     * @author wangbb
     * @description postform提交需要重试机制
     * @date 创建时间：2017年4月11日  下午5:16:04
     * @version 1.0.0.0
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
	public static String postFormHostParseRetry(String url, final Map<String, Object> paramsMap) throws IOException {
		int count = 1;
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		if (paramsMap != null) {
			for (String key : paramsMap.keySet()) {
				formEncodingBuilder.add(key, String.valueOf(paramsMap.get(key)));
			}
		}
		Request request = new Request.Builder().url(url).post(formEncodingBuilder.build()).build();
		String result = null;
		do {
			Response response = client.newCall(request).execute();
			result = response.body().string();
			if(response.isSuccessful()){
				break;
			}else if (result.contains("unknown")) {
				count++;
			}else{
				throw new IOException(result);
			}
		} while (count <= 3);
		return result;
	}


    /**
     * 表单post提交   application/x-www-form-urlencoded
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static Response postFormWithResponse(String url, final Map<String, Object> paramsMap) throws IOException {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                formEncodingBuilder.add(key, String.valueOf(paramsMap.get(key)));
            }
        }
        Request request = new Request.Builder().url(url).post(formEncodingBuilder.build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response;
        } else {
            throw new IOException(response.body().string());
        }
    }

    /**
     * 表单post提交   multipart/form-data
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     * @author junyan
     * @date 2016年7月19日 下午5:28:28
     */
    public static String postMultiPartForm(String url, final Map<String, Object> paramsMap) throws IOException {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value instanceof byte[]) {
                    byte[] bytes = (byte[]) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, bytes));
                } else if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, file));
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
        }

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else if (response.code() == 413) {
            throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("请求流量超过限制大小,请压缩处理后重试"), "请求流量超过限制大小,请压缩处理后重试");
        } else {
            throw new IOException(response.body().string());
        }
    }

    /**
     * @author wangbb
     * @description 表单post提交（可以重试）
     * @date 创建时间：2017年4月11日  下午5:23:28
     * @version 1.0.0.0
     * @param url
     * @param paramsMap
     * @param count 重试次数
     * @return
     * @throws IOException
     */
    public static String postFileHostParseRetry(String url, final Map<String, Object> paramsMap) throws IOException {
        int count = 1;
    	MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value instanceof byte[]) {
                    byte[] bytes = (byte[]) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, bytes));
                } else if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, file));
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        String result=null;
        do{
        	 Response response = client.newCall(request).execute();
        	 result = response.body().string();
             if(response.isSuccessful()){
            	 break;
             }else if (response.code() == 413) {
                 throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("请求流量超过限制大小,请压缩处理后重试"), "请求流量超过限制大小,请压缩处理后重试");
             } else {
	            if(result.contains("unknown")){
	            	count++;
	            }else{
	            	throw new IOException(result);
	            }
             }
        }while(count<=3);

        return result;

    }

    public static byte[] getPostByte(String url, final Map<String, Object> paramsMap) throws IOException {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (paramsMap != null) {
            for (String key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if (value instanceof byte[]) {
                    byte[] bytes = (byte[]) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, bytes));
                } else if (value instanceof File) {
                    File file = (File) value;
                    builder.addFormDataPart(key, key, RequestBody.create(MultipartBuilder.FORM, file));
                } else {
                    builder.addFormDataPart(key, String.valueOf(value));
                }
            }
        }

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().bytes();
        } else if (response.code() == 413) {
            throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("请求流量超过限制大小,请压缩处理后重试"), "请求流量超过限制大小,请压缩处理后重试");
        } else {
            throw new IOException(response.body().string());
        }
    }



    public static void main(String[] args) throws Exception {
        //getTest();
		postFormTest();
//        test();
//        String url = "http://101.71.241.100:8036/channel/bankcard";
//        Request request = new Request.Builder().header("Cookie", "token=14E6693C01B64F51991E20B6972834AB.660BA732D36A205F93F183F8542AC185").url(url).build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
//        System.out.println(response.isSuccessful());
    }

    public static void test() {
        String json = URLUtil.decode("data=%7B%22verify_result%22%3A+%7B%22time_used%22%3A+1079%2C+%22id_exceptions%22%3A+%7B%22id_photo_monochrome%22%3A+0%2C+%22id_attacked%22%3A+0%7D%2C+%22request_id%22%3A+%221469257550%2C51848cbe-3ea7-4b03-824a-21425fecad9a%22%2C+%22face_genuineness%22%3A+%7B%22synthetic_face_threshold%22%3A+0.5%2C+%22synthetic_face_confidence%22%3A+0.0%2C+%22mask_confidence%22%3A+0.0%2C+%22mask_threshold%22%3A+0.5%7D%2C+%22result_faceid%22%3A+%7B%22confidence%22%3A+91.013%2C+%22thresholds%22%3A+%7B%221e-3%22%3A+65.3%2C+%221e-5%22%3A+76.5%2C+%221e-4%22%3A+71.8%2C+%221e-6%22%3A+79.9%7D%7D%7D%2C+%22biz_no%22%3A+%22123%22%2C+%22biz_extra_data%22%3A+%22%22%2C+%22liveness_result%22%3A+%7B%22result%22%3A+%22success%22%2C+%22version%22%3A+%22MegLive+2.4.0L%22%2C+%22log%22%3A+%221469257541%5Cn0%3AA%5Cn3237%3AE%5Cn4565%3AP%5Cn6277%3AM%5Cn8227%3AO%5Cn%22%2C+%22failure_reason%22%3A+%22%22%7D%2C+%22biz_id%22%3A+%221469257536%2C9a135c16-51ac-4971-87b7-1c98fc6d11cd%22%7D&sign=ee338c59b36d225aebabab5c4fe786aea8f929c8", "utf-8");
        System.err.println(json);
    }

    private static void postFormTest() throws Exception {
        String url = "http://121.40.159.135/account/user/login";
        Map<String, Object> map = Maps.newHashMap();
        map.put("phone", "18012341234");
        map.put("password", "123456");
        map.put("email", "123456789@163.com");
        String res = postForm(url, map);
        System.out.println(res);
    }

    private static void postTest() throws Exception {
        //String url = "http://192.168.2.198:8081/Baitiao/orderNo/CL";
        String url = "hzweisc.u1.luyouxia.net:56243/channel/channel_sys_user/getToken";
        Map<String, Object> json = new HashMap<String, Object>(3);
//		{
//			"customerId":"201504111402464257bdf665c8f324a60bc6b72766a2655aa",
//			"certificateNumber":"410108198510200109",
//			"fullname":"刘聪",
//			"mobilePhone":"13838352955",
//			"subProductCode":"FDSEJ002",
//			"applyAmount":"1200",
//			"orderNo":"FDSEJ00120160612088"
//        {
//            "mobilePhone":"13995308250",
//                "sId":"551D404E34224946BC419FA4676A3463",
//                "userSource":"QD000026"
//        }
//		}
        json.put("mobilePhone", "13995308250");
        json.put("sId", "551D404E34224946BC419FA4676A3463");
        json.put("userSource", "QD000026");
        Response response = postResponse(url, JSONObject.toJSONString(json));
        String cookie = response.header("Set-Cookie");
        String token = cookie.split(";")[0].substring(6);
        System.out.println(token);
    }

    private static void getTest() throws Exception {
        //发送请求，获取数据
        String url = "http://172.30.249.28/api/divisor/detail?user_id={0}&fields={1}";
        String userId = "2015031514284075585d69e096b4945cbaca485a547093028";
        String fields = "{'factor_code':'realname_user'},{'factor_code':'th_invest_r_all','sub_factor_code':['th_invest_r_account_balance','th_invest_r_operation_day','th_invest_r_order_number', 'th_invest_r_product_name','th_invest_r_investment_amount','th_invest_r_product_type','th_invest_r_uncollected_revenue','th_invest_r_name','th_invest_r_product_deadline','th_invest_r_maturity_day','th_invest_r_reimbursement','th_invest_r_state','th_invest_r_account','th_invest_r_breath_day']}";
        url = MessageFormat.format(url, userId, URLUtil.encode(fields));
        System.out.println("url = " + url);
        String result = get(url);
        System.out.println("------------");
        System.out.println("返回密文 = " + result);
        System.out.println("------------");

        //解密数据  云提供的RSA私钥
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKKlDyvE+l23Fhmoh1xrr/Lvcl9LmnN3nHFVI5afrKs/5Ztko4RPyjZdFefvqteD8TCjq2Q3fHMFnVY4pZvHdozgUd8ujFlB10nJeVu6IN8/azcutI3+TGU54/SQoRiuahtyjfoeCdrSf41IFPoMXDO1iPPtd+dk2A+Kzhf8Cto7AgMBAAECgYBeETkFB5gGO638iQxA18ebqgFsPD3IKpXAto0uwIsNQJd7mGk6TSuW5Z+V1XfpzdXhW8f03mKL4+Nryrd9sY9OXWbGh/K973p5OHp2j3IuEM3NajwCs/KS71RBEdEE4QAgxCKEWr8hDESi5uPBYHJR+O8jBEW4iwBa1A3bi2UeAQJBAOZwuzWmxn7LX6o4J9POwmHcE3m/V7GCEpj99L4+QtDK4Hd65YogUPXRrSMF6l+HganbXmvbFdGcFlowl36N1TECQQC0r0wScegdwyE2yZWFcBAYDIEEwJs7CE3cZG4BKbuzcZ24mAxGhBikxcNSgheJCYWOUW8fACZKjmK8tq7lAfsrAkEAwLjjOPmj5IL+HQQd+vxegS1ndFjcZG5eU2mA+GJlyu0BUauMsTpZu1yCqlnWK9LOVFpM23M9CjK6Vu9wo7zG4QJBALR1bCXz7B9llxOwLAz0yq4qNuW3NJK2DYW0LIYu11A2ho8qFQCMyEwxVI5gbf8pKmHlBXXR4WyPXyAZ2P3Sh7cCQQDASBCVF15SoTMht9CLXLpQ6l8CjtRCatBpAaEgAwTPRI7jeAggm9R+nOpZW7ADqqrGs+Sci0jOZ+XTUi9OQxtu";
        String ming = RSAUtil.decryptByPrivateKey(URLUtil.decode(result), privateKey);
        System.out.println("明文信息 = " + ming);

        //处理数据
        tran(ming);
    }

    private static void tran(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        String userId = String.valueOf(jsonObject.get("user_id"));
        System.out.println("userId = " + userId);
        JSONArray fields = (JSONArray) jsonObject.get("fields");
        if (fields.size() < 1) {
            return;
        }
        System.out.println("fields not null------------");
        JSONObject a = (JSONObject) fields.get(0);
        JSONObject b = (JSONObject) fields.get(1);
        JSONObject c = a;
        if (a.get("factor_code").equals("realname_user")) {
            c = b;
        }

        JSONArray values = (JSONArray) c.get("value");

        for (int i = 0; i < values.size(); i++) {
            JSONArray array = (JSONArray) values.get(i);
            System.out.println("=============================================================================");
            for (int j = 0; j < array.size(); j++) {
                JSONObject obj = (JSONObject) array.get(j);
                System.out.println(obj.get("factor_code") + "------" + obj.get("value"));
            }
            System.out.println("==============================================================================");
        }

        System.out.println(values.size());

    }

}
