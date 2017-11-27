package com.wsc.qa.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.qa.dao.FengdaiUserInfoDao;
import com.wsc.qa.service.FengdaiUserInfoService;
@Service
public class FengdaiUserInfoServiceImpl implements FengdaiUserInfoService{

	@Resource
	private FengdaiUserInfoDao fengdaiUserInfoDao;
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
