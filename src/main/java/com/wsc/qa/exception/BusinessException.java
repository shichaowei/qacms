package com.wsc.qa.exception;

import com.wsc.qa.constants.CommonConstants.ErrorCode;

/** 
 * 业务异常 
 * @description TODO 
 * @author chen.gs 
 * @create date 2016年4月28日 
 * @modified by  
 * @modify date 
 * @version v1.0 
 */  
public class BusinessException extends RuntimeException {

	/***/
	private static final long serialVersionUID = -2824630902714825963L;

	private ErrorCode errorCode;
	private String detailMsg;

	public BusinessException(){	
	}
	
	public BusinessException(ErrorCode error,String detailMsg) {
		super(detailMsg);
		this.errorCode = error;
		this.detailMsg = detailMsg;
	}
	
	public BusinessException(ErrorCode error) {
		super(error.getDescription());
		this.errorCode = error;
	}

	public BusinessException(ErrorCode error, Throwable e) {
		super(error.getDescription(), e);
		this.errorCode = error;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetailMsg() {
		return detailMsg;
	}

	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	
} 