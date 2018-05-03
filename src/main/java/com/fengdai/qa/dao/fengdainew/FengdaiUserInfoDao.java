package com.fengdai.qa.dao.fengdainew;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FengdaiUserInfoDao {

	private static final Logger logger = LoggerFactory.getLogger(FengdaiUserInfoDao.class);
//	@Resource(name="newFDJdbcTemplate")
	@Resource
	private JdbcTemplate jdbcTemplate;

	public String deleteAllLoanByLoginname(String loginname) {
		

	}
	public void deleteAllLoanWithoutCreditByLoginname(String loginname) {

	}
	public String deleteUserByLoginname(String loginname) {
		
	}

	public String deleteLoanByLoanName(String loanname) {
		

	}
	public String deleteLoanByLoanId(String loanapplyid) {
		
	}
	public String changeProcessSQDToLoanning(String loanname) {
		
	}

	public String changeSQDToLoanning(String loanname) {

		
	}

	public String changeUserAccount(String username,BigDecimal moneynum) {
		

	}




}
