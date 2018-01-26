package com.fengdai.qa.constants;

import com.alibaba.fastjson.JSONObject;

/**
 *
 *
 * @author hzweisc
 *
 */
public class CommonConstants {




	public static enum ShellInfo{
		restanyproxyrule("ruledemo.txt");
		private final String value;
		private ShellInfo(String value) {
			this.value=value;
		}
		public String getValue(){
			return value;
		}

	}

	public static String DINGDINGJQR = "https://oapi.dingtalk.com/robot/send?access_token=c3ed6cf17e208e48047c39de15c25d5b7052885140a58e3e2501bb88cff6f9f4";

	public static String creditupdatejob="1023";

	/**
	 *
	 * @author hzweisc
	 *
	 */
	public static enum deleteCode{
		//通过登录名删除所有单子
		deleteAllLoanByLoginname("deleteAllLoanByLoginname"),
		deleteUserByLoginname("deleteUserByLoginname"),
		deleteLoanByLoanName("deleteLoanByLoanName"),
		deleteLoanByLoanId("deleteLoanByLoanId"),
		changeSQDToLoanning("changeSQDToLoanning"),
		changeProcessSQDToLoanning("changeProcessSQDToLoanning"),
		changeUserAmount("changeUserAmount");
		private final String value;
		deleteCode(String value) {
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}

	/**
	 *
	 * @author hzweisc
	 *
	 */
	public static enum opertype{
		//item项
		mockdata("mock数据"),
		getservettime("获取服务器时间"),
		changtime("修改时间"),
		fixfengdaienv("修复环境"),
		deletefengdaidata("删除蜂贷用户业务数据"),
		fundscallbackfengdai("资金服回调fengdai业务"),
		startmock("开始mock"),
		deletemockrules("删除mock规则"),
		gethttpinterface("获取特定http请求的返回值"),
		logtome("获取服务器端的日志");
		private final String value;
		opertype(String value) {
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}
	/**
	 *
	 * @author hzweisc
	 *
	 */
	public static enum callbackType{
		//生成回调报文
		virRelateId("virRelateId"),
		virRemark("virRemark");
		private final String value;
		callbackType(String value) {
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}




    /**
     * @Description 错误代码
     */
    public static enum ErrorCode {
    	//通用错误code
        ERROR_ACCOUTWRONG(0x00001, "账户密码不正确"),
        ERROR_REPEAT_REQUEST(0x00003, "重复申请"),
        ERROR_PARAMS_INVALIED(0x00003, "参数非法"),
        ERROR_REQUEST_TIMEOUT(0x00005, "请求过期"),
        ERROR_IP_BLOCKED(0xFF000, "您的 ip 被限制访问"),
        ERROR_ILLEGAL_PARAMTER(0xFF001, "缺少参数或参数不合法"),
        ERROR_ILLEGAL_USERNAME(0xFF003, "注册的账号已被使用"),
        ERROR_ILLEGAL_REQUEST(0xFF002, "http請求不合法"),
        ERROR_ILLEGAL_DB(0xFF004, "非法DB参数"),
      //自定义消息用,BusinessException构造函数时用传detailMsg
        ERROR_OTHER_MSG(0xFF999, "自定义消息用"),
        ERROR_TEMP(0xFF000, "临时变量，为了保留原先code值")
        ;


        private Integer code;
        private String description;
        private String tempDesc;

        public String getTempDesc() {
            return tempDesc;
        }

        public void setTempDesc(String tempDesc) {
            this.tempDesc = tempDesc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public ErrorCode customDescription(String customError) {
            ERROR_TEMP.code = code;
            ERROR_TEMP.description = description;
            if (customError != null) {
                this.description = customError;
            }

            return this;
        }

        ErrorCode(Integer code, String description) {
            this.code = code;
            this.description = description;
        }



        @Override
		public String toString() {
            return toString(null);
        }

        /**
         * 用于返回注解描述信息
         *
         * @param customerDescription
         * @return
         */
        public String toString(String customerDescription) {
            JSONObject json = new JSONObject();
            JSONObject error = new JSONObject();
            error.put("code", code);
            String userDefinedDescrption = description;
            if (customerDescription != null && customerDescription.length() > 0) {
                userDefinedDescrption = userDefinedDescrption + ";" + customerDescription;
            }
            error.put("description", userDefinedDescrption);
            json.put("error", error);

            return json.toString();
        }
    }


	public static void main(String[] args) {
		System.out.println(deleteCode.valueOf("deleteAllLoanByLoginname").getValue());

		String var = String.format(ServerInfo.restartanyproxyShellMode, CommonConstants.ShellInfo.restanyproxyrule.getValue());
		System.out.println(var);
	}



}
