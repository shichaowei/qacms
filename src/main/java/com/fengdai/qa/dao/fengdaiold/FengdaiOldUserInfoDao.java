package com.fengdai.qa.dao.fengdaiold;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FengdaiOldUserInfoDao {

	private static final Logger logger = LoggerFactory.getLogger(FengdaiOldUserInfoDao.class);
//	@Resource(name="oldFDJdbcTemplate")
//	private JdbcTemplate jdbcTemplate;

	@Resource
	private JdbcTemplate jdbcTemplate;

	public void deleteAllLoanByLoginname(String loginname) {
		
		
	}
	public void deleteAllLoanWithoutCreditByLoginname(String loginname) {

	}
	public void deleteUserByLoginname(String loginname) {
		
	}

	public void deleteLoanByLoanName(String loanname) {
		

	}
	public void deleteLoanByLoanId(String loanapplyid) {
		
	}
	public void changeProcessSQDToLoanning(String loanname) {
		

	}

	public void changeSQDToLoanning(String loanname) {

		
	}

	public void changeUserAccount(String username,BigDecimal moneynum) {
		

	}




}
