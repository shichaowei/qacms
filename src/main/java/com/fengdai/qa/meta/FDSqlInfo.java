package com.fengdai.qa.meta;

import java.util.Date;

public class FDSqlInfo {

	private int id;
	private String sql;
	private String reverseresult;
	private String businessJdbcUrl ;
	private String businessJdbcName ;
	private String businessJdbcPassword ;
	private Date datetime;



	public String getBusinessJdbcUrl() {
		return businessJdbcUrl;
	}
	public void setBusinessJdbcUrl(String businessJdbcUrl) {
		this.businessJdbcUrl = businessJdbcUrl;
	}
	public String getBusinessJdbcName() {
		return businessJdbcName;
	}
	public void setBusinessJdbcName(String businessJdbcName) {
		this.businessJdbcName = businessJdbcName;
	}
	public String getBusinessJdbcPassword() {
		return businessJdbcPassword;
	}
	public void setBusinessJdbcPassword(String businessJdbcPassword) {
		this.businessJdbcPassword = businessJdbcPassword;
	}
	public String getReverseresult() {
		return reverseresult;
	}
	public void setReverseresult(String reverseresult) {
		this.reverseresult = reverseresult;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql.replaceAll("\r|\n|\\s", " ").replaceAll("\\s+", " ");
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}




}
