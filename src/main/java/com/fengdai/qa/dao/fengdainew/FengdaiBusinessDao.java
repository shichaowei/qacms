package com.fengdai.qa.dao.fengdainew;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FengdaiBusinessDao {
	private static final Logger logger = LoggerFactory.getLogger(FengdaiUserInfoDao.class);
//	@Resource(name="newFDJdbcTemplate")
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

	public String getmongodbid(String loanapplyid) {String sql="SELECT apply_info_mongo_id FROM fengdai_riskcontrol.loan_apply WHERE id=?";
		String result =jdbcTemplate.queryForObject(sql, String.class, loanapplyid);
		logger.info("sql执行结果为：",result);
		return result;
	}

	public String getfieldname(String fieldid) {
		
		String result =jdbcTemplate.queryForObject(sql, String.class, fieldid);
		logger.info("sql执行结果为：",result);
		return result;
	}

}
