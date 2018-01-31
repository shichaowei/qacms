package com.fengdai.qa.constants;

public class EnvInfo {

	public static enum callbackUrlCode{
		//通过登录名删除所有单子
		callbackold("http://10.200.141.66:8080/Mqnotify/notify/transfer"),
		callbacknew("http://10.200.141.97:9010/notify/transfer"),
		callbacknewonline("http://10.200.141.18:9010/notify/transfer");
		private final String value;
		callbackUrlCode(String value) {
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}

}
