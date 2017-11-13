package com.wsc.qa.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FengdaiDao {
	private static final Logger logger = LoggerFactory.getLogger(FengdaiUserInfoDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String  getremarkViaRelate(String relateid) {
		String sql = "select remarks FROM fengdai_mqnotify.mc_business WHERE relate_id ='"+relateid+"' ORDER BY create_date DESC LIMIT 1";
//		System.out.println(sql);
		logger.info("sql执行结果为：",jdbcTemplate.queryForMap(sql));
		return (String) jdbcTemplate.queryForMap(sql).get("remarks");
	}


	public String  getremarkViaRelateNew(String relateid) {
		String sql = "select remarks FROM fengdai_finance.mc_business WHERE relate_id ='"+relateid+"' ORDER BY create_date DESC LIMIT 1";
//		System.out.println(sql);
		logger.info("sql执行结果为：",jdbcTemplate.queryForMap(sql));
		return (String) jdbcTemplate.queryForMap(sql).get("remarks");
	}

}
