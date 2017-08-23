package com.wsc.qa.test;


import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.*;

public class TestJson {

	
	
	public class item {
		private String orderId;
		private double amount;
		private String status;
		private String userParams ;
		
		public item(String orderId, double amount, String status, String userParams) {
			this.orderId = orderId;
			this.amount = amount;
			this.status = status;
			this.userParams = userParams;
		}
		
		public item( double amount, String status) {
			this.amount = amount;
			this.status = status;
		}

		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getUserParams() {
			return userParams;
		}
		public void setUserParams(String userParams) {
			this.userParams = userParams;
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		
	String jsonStr="[{\"billId\":\"204af517368e4b8ab45087ddcb8f98fe\",\"outAmount\":4000.0,\"inAcctCode\":\"739E9ADDB56E4B249738A58C32B20B33\",\"inAcctType\":\"00\",\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":\"还款\",\"tyfTradeType\":\"10|11|12|14|4201\",\"tyfAtomTypeInfo\":[{\"atomType\":\"10\",\"atomTypeAmount\":4000.0,\"atomTypeDescription\":\"[白领通-闪电公积金2FDBLTC00120170731001]\"}],\"notifyUrl\":\"http://172.30.250.25:8080/Mqnotify/notify/transfer\",\"customJson\":\"{\\\"mcId\\\":\\\"00ca88e110354741ae1d515e298315f7\\\",\\\"messageReqNo\\\":\\\"DepositCallBackBillReqNo20170731174200361QD00003201471\\\",\\\"repayManner\\\":\\\"PRE\\\",\\\"channel\\\":\\\"QD000032\\\",\\\"repayKh\\\":\\\"4211.600000\\\",\\\"content\\\":\\\"{\\\\\\\"action\\\\\\\":2,\\\\\\\"loanOrderCode\\\\\\\":\\\\\\\"150148846901273713\\\\\\\",\\\\\\\"repayId\\\\\\\":222}\\\"}\",\"platfCode\":\"FYD_LOAN\",\"platfName\":\"蜂e贷\",\"payChannel\":\"lianlian\",\"atomicFunds\":false},{\"billId\":\"ff2a2350be1b4758a48670cd3c590509\",\"outAmount\":78.12,\"inAcctCode\":\"739E9ADDB56E4B249738A58C32B20B33\",\"inAcctType\":\"00\",\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":\"还款\",\"tyfTradeType\":\"10|11|12|14|4201\",\"tyfAtomTypeInfo\":[{\"atomType\":\"11\",\"atomTypeAmount\":78.12,\"atomTypeDescription\":\"[白领通-闪电公积金2FDBLTC00120170731001]\"}],\"notifyUrl\":\"http://172.30.250.25:8080/Mqnotify/notify/transfer\",\"customJson\":\"{\\\"mcId\\\":\\\"00ca88e110354741ae1d515e298315f7\\\",\\\"messageReqNo\\\":\\\"DepositCallBackBillReqNo20170731174200361QD00003201471\\\",\\\"repayManner\\\":\\\"PRE\\\",\\\"channel\\\":\\\"QD000032\\\",\\\"repayKh\\\":\\\"4211.600000\\\",\\\"content\\\":\\\"{\\\\\\\"action\\\\\\\":2,\\\\\\\"loanOrderCode\\\\\\\":\\\\\\\"150148846901273713\\\\\\\",\\\\\\\"repayId\\\\\\\":222}\\\"}\",\"platfCode\":\"FYD_LOAN\",\"platfName\":\"蜂e贷\",\"payChannel\":\"lianlian\",\"atomicFunds\":false},{\"billId\":\"22602d36ee774373807ce2793e5568b4\",\"outAmount\":33.48,\"inAcctCode\":\"00000005\",\"inAcctType\":\"01\",\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":\"还款\",\"tyfTradeType\":\"10|11|12|14|4201\",\"tyfAtomTypeInfo\":[{\"atomType\":\"12\",\"atomTypeAmount\":33.48,\"atomTypeDescription\":\"[白领通-闪电公积金2FDBLTC00120170731001]\"}],\"notifyUrl\":\"http://172.30.250.25:8080/Mqnotify/notify/transfer\",\"customJson\":\"{\\\"mcId\\\":\\\"00ca88e110354741ae1d515e298315f7\\\",\\\"messageReqNo\\\":\\\"DepositCallBackBillReqNo20170731174200361QD00003201471\\\",\\\"repayManner\\\":\\\"PRE\\\",\\\"channel\\\":\\\"QD000032\\\",\\\"repayKh\\\":\\\"4211.600000\\\",\\\"content\\\":\\\"{\\\\\\\"action\\\\\\\":2,\\\\\\\"loanOrderCode\\\\\\\":\\\\\\\"150148846901273713\\\\\\\",\\\\\\\"repayId\\\\\\\":222}\\\"}\",\"platfCode\":\"FYD_LOAN\",\"platfName\":\"蜂e贷\",\"payChannel\":\"lianlian\",\"atomicFunds\":false},{\"billId\":\"95aa3a59a76a423ba6e73184a75c1293\",\"outAmount\":40.0,\"inAcctCode\":\"739E9ADDB56E4B249738A58C32B20B33\",\"inAcctType\":\"00\",\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":\"还款\",\"tyfTradeType\":\"10|11|12|14|4201\",\"tyfAtomTypeInfo\":[{\"atomType\":\"14\",\"atomTypeAmount\":40.0,\"atomTypeDescription\":\"[白领通-闪电公积金2FDBLTC00120170731001]\"}],\"notifyUrl\":\"http://172.30.250.25:8080/Mqnotify/notify/transfer\",\"customJson\":\"{\\\"mcId\\\":\\\"00ca88e110354741ae1d515e298315f7\\\",\\\"messageReqNo\\\":\\\"DepositCallBackBillReqNo20170731174200361QD00003201471\\\",\\\"repayManner\\\":\\\"PRE\\\",\\\"channel\\\":\\\"QD000032\\\",\\\"repayKh\\\":\\\"4211.600000\\\",\\\"content\\\":\\\"{\\\\\\\"action\\\\\\\":2,\\\\\\\"loanOrderCode\\\\\\\":\\\\\\\"150148846901273713\\\\\\\",\\\\\\\"repayId\\\\\\\":222}\\\"}\",\"platfCode\":\"FYD_LOAN\",\"platfName\":\"蜂e贷\",\"payChannel\":\"lianlian\",\"atomicFunds\":false},{\"billId\":\"0f4dbf60526945f8815e3f9a1ca7ae59\",\"outAmount\":60.0,\"inAcctCode\":\"00000005\",\"inAcctType\":\"01\",\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":\"还款\",\"tyfTradeType\":\"10|11|12|14|4201\",\"tyfAtomTypeInfo\":[{\"atomType\":\"4201\",\"atomTypeAmount\":60.0,\"atomTypeDescription\":\"[白领通-闪电公积金2FDBLTC00120170731001]\"}],\"notifyUrl\":\"http://172.30.250.25:8080/Mqnotify/notify/transfer\",\"customJson\":\"{\\\"mcId\\\":\\\"00ca88e110354741ae1d515e298315f7\\\",\\\"messageReqNo\\\":\\\"DepositCallBackBillReqNo20170731174200361QD00003201471\\\",\\\"repayManner\\\":\\\"PRE\\\",\\\"channel\\\":\\\"QD000032\\\",\\\"repayKh\\\":\\\"4211.600000\\\",\\\"content\\\":\\\"{\\\\\\\"action\\\\\\\":2,\\\\\\\"loanOrderCode\\\\\\\":\\\\\\\"150148846901273713\\\\\\\",\\\\\\\"repayId\\\\\\\":222}\\\"}\",\"platfCode\":\"FYD_LOAN\",\"platfName\":\"蜂e贷\",\"payChannel\":\"lianlian\",\"atomicFunds\":false},{\"billId\":null,\"outAmount\":4211.6,\"inAcctCode\":null,\"inAcctType\":null,\"outAcctCode\":\"310E7B8CD3654EBE840986E2C2848521\",\"outAcctType\":\"00\",\"tyfTradeName\":null,\"tyfTradeType\":null,\"tyfAtomTypeInfo\":null,\"notifyUrl\":null,\"customJson\":null,\"platfCode\":\"FYD_LOAN\",\"platfName\":null,\"payChannel\":\"lianlian\",\"atomicFunds\":true}]";	
	
	List<String> orderId = JsonPath.read ( jsonStr, "$..billId");
		List<Double> amount = JsonPath.read ( jsonStr, "$..outAmount");
		List<String> userParams = JsonPath.read ( jsonStr, "$..customJson");
		System.out.println(orderId.size());
		ArrayList<TestJson.item> items = new ArrayList<>();
		for(int i=0;i<orderId.size();i++) {
			if(orderId.get(i)!=null) {
				items.add(new TestJson().new item(orderId.get(i), amount.get(i), "PAY_SUCCESS", userParams.get(i)));
			}else {
				items.add(new TestJson().new item( amount.get(i), "PAY_SUCCESS"));
			}
			
		}	
		String jsonString = JSON.toJSONString(items);

		System.out.println(jsonString);
		
		
	}
	


}
