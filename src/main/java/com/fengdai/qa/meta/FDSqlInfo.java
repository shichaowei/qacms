package com.fengdai.qa.meta;

import java.util.Date;

public class FDSqlInfo {

	private int id;
	private String sql;
	private Date datetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSql() {
		return sql.replaceAll("\n", " ");
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}




}
