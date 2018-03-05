package com.fengdai.qa.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author hzweisc
 *
 */

import com.fengdai.qa.meta.FDSqlInfo;
import com.fengdai.qa.service.FengdaiSqlService;
import com.fengdai.qa.utils.LocalDateTimeUtils;
@Controller
public class FDInterfaceSqlController {

	private static final Logger logger = LoggerFactory.getLogger(FDInterfaceSqlController.class);

	@Autowired
	public FengdaiSqlService fengdaiSqlServiceImpl;

	@RequestMapping({ "sqlprocess" })
	public void getIndex(FDSqlInfo fdSqlInfo,HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		org.springframework.jdbc.datasource.DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(fdSqlInfo.getBusinessJdbcUrl());
		dataSource.setUsername(fdSqlInfo.getBusinessJdbcName());
		dataSource.setPassword(fdSqlInfo.getBusinessJdbcPassword());
		String sql=fdSqlInfo.getSql();

		String checkNumSQL="";
		String reverseresult="";
		if(sql.toLowerCase().indexOf("from") >0  && sql.trim().toLowerCase().matches("^select.*")){
			checkNumSQL="SELECT count(*) "+sql.substring(sql.toLowerCase().indexOf("from"));
		}
		if(sql.toLowerCase().indexOf("where") >0 && sql.trim().toLowerCase().matches("^update.*")){
			String tablename=sql.toLowerCase().split("update")[1].split("set")[0].trim();
			checkNumSQL="SELECT count(*) from "+tablename+" "+sql.substring(sql.toLowerCase().indexOf("where"));
		}
		if(sql.toLowerCase().indexOf("from") >0  && sql.trim().toLowerCase().matches("^delete.*")){
			checkNumSQL="SELECT count(*) "+sql.substring(sql.toLowerCase().indexOf("from"));
		}

		org.springframework.jdbc.core.JdbcTemplate jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource);
		if (sql.trim().toLowerCase().matches("^select.*") || sql.trim().toLowerCase().matches("^update.*") ||sql.trim().toLowerCase().matches("^delete.*")) {
			Long sqlsize = (Long) jdbcTemplate.queryForMap(checkNumSQL).get("count(*)");
			String selectSQL=checkNumSQL.replace("count(*)", "*");
			if (sqlsize.longValue() > 1) {
				reverseresult = com.alibaba.fastjson.JSON.toJSONString(jdbcTemplate.queryForList(selectSQL));
			} else if (sqlsize == 1) {
				reverseresult = com.alibaba.fastjson.JSON.toJSONString(jdbcTemplate.queryForMap(selectSQL));
			}
		}
		if(sql.trim().toLowerCase().matches("^insert.*")){
			String deleteSQL="";
		}
		fdSqlInfo.setReverseresult(reverseresult);
		logger.info("fdsqlinfo is {}",ToStringBuilder.reflectionToString(fdSqlInfo));
		fengdaiSqlServiceImpl.addSql(fdSqlInfo);
		System.out.println(LocalDateTimeUtils.formatTime(LocalDateTimeUtils.convertDateToLDT(fengdaiSqlServiceImpl.getDbtime()), "yyyy-MM-dd HH:mm:ss"));


	}

}
