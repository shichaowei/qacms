package com.fengdai.qa.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengdai.qa.annotation.DS;
import com.fengdai.qa.constants.DataSourceConsts;
import com.fengdai.qa.dao.fengdaiold.FengdaiOldUserInfoDao;
import com.fengdai.qa.service.FengdaiDbOldUserInfoService;
@Service
@DS(value=DataSourceConsts.fengdai2)
public class FengdaiDbOldUserInfoServiceImpl implements FengdaiDbOldUserInfoService{
	@Autowired
	private FengdaiOldUserInfoDao fengdaiUserInfoDao;
	@Override
	public void deleteAllLoanByLoginname(String loginname) {

		fengdaiUserInfoDao.deleteAllLoanByLoginname(loginname);


	}
	@Override
	public void deleteAllLoanWithoutCreditByLoginname(String loginname) {
		// TODO Auto-generated method stub

	}
	@Override
	public void deleteUserByLoginname(String loginname) {

		fengdaiUserInfoDao.deleteUserByLoginname(loginname);
	}
	@Override
	public void deleteLoanByLoanName(String loanname) {

		fengdaiUserInfoDao.deleteLoanByLoanName(loanname);
	}
	@Override
	public void deleteLoanByLoanId(String loanapplyid) {

		fengdaiUserInfoDao.deleteLoanByLoanId(loanapplyid);
	}
	@Override
	public void changeSQDToLoanning(String loanname) {
		fengdaiUserInfoDao.changeSQDToLoanning(loanname);
	}
	@Override
	public void changeProcessSQDToLoanning(String loanname) {
		fengdaiUserInfoDao.changeProcessSQDToLoanning(loanname);
	}
	@Override
	public void changeUserAccount(String username, BigDecimal moneynum) {
		fengdaiUserInfoDao.changeUserAccount(username, moneynum);

	}


}
