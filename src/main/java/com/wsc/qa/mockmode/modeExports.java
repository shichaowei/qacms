package com.wsc.qa.mockmode;

public class modeExports {
	
	modeBeforeSendRequestBody beforeSendRequestBody;

	public modeBeforeSendRequestBody getBeforeSendRequestBody() {
		return beforeSendRequestBody;
	}

	public modeExports setBeforeSendRequestBody(modeBeforeSendRequestBody beforeSendRequestBody) {
		this.beforeSendRequestBody = beforeSendRequestBody;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(String.format("module.exports={%s}",beforeSendRequestBody.toString()));
		return result.toString();
	}
	
	

}
