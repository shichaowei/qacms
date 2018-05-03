package com.fengdai.qa.dao.fengdaiold;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FengdaiOldDao {
	private static final Logger logger = LoggerFactory.getLogger(FengdaiOldUserInfoDao.class);
//	@Resource(name="oldFDJdbcTemplate")
	@Resource
	private JdbcTemplate jdbcTemplate;

	public String  getremarkViaRelate(String relateid) {
		
		logger.info("sql执行结果为：",jdbcTemplate.queryForMap(sql));
		return (String) jdbcTemplate.queryForMap(sql).get("remarks");
	}


	public String  getremarkViaRelateNew(String relateid) {
		
		logger.info("sql执行结果为：",jdbcTemplate.queryForMap(sql));
		return (String) jdbcTemplate.queryForMap(sql).get("remarks");
	}

}
