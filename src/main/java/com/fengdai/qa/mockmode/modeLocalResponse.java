package com.fengdai.qa.mockmode;

public class modeLocalResponse {

	public class headerDetail{
		private String ContentType;
		private String Connection;
		private String CacheControl;
		public String getContentType() {
			return ContentType;
		}
		public headerDetail setContentType(String contentType) {
			ContentType = contentType;
			return this;
		}
		public String getConnection() {
			return Connection;
		}
		public headerDetail setConnection(String connection) {
			Connection = connection;
			return this;
		}
		public String getCacheControl() {
			return CacheControl;
		}
		public headerDetail setCacheControl(String cacheControl) {
			CacheControl = cacheControl;
			return this;
		}

		@Override
		public String toString() {
			return String.format("'Content-Type':'%s',Connection:'%s','Cache-Control':'%s'", ContentType , Connection, CacheControl);
		}


	}


	private int statusCode;
	private headerDetail header;
	private String body;
	//增加超时判断
	private int delaytime;

	public int getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(int delaytime) {
		this.delaytime = delaytime;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public modeLocalResponse setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	public headerDetail getHeader() {
		return header;
	}
	public modeLocalResponse setHeader(headerDetail header) {
		this.header = header;
		return this;
	}
	public String getBody() {
		return body;
	}
	public modeLocalResponse setBody(String body) {
		this.body = body;
		return this;
	}
	@Override
	public String toString() {
		StringBuffer result= new StringBuffer();
		result.append(String.format("statusCode:%d,", statusCode));
		result.append(String.format("header:{%s},", header.toString()));
		result.append(String.format("body:'%s'", body));
		return result.toString();
	}




}
