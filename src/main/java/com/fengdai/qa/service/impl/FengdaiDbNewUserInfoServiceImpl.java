package com.fengdai.qa.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fengdai.qa.dao.fengdainew.FengdaiUserInfoDao;
import com.fengdai.qa.service.FengdaiDbNewUserInfoService;
@Service
//由于存在多个数据库 多套环境 此处不加注解 切换放到controller层做datasource切换
//@DS(value=DataSourceConsts.fengdai3)
public class FengdaiDbNewUserInfoServiceImpl implements FengdaiDbNewUserInfoService{

	@Resource
	private FengdaiUserInfoDao fengdaiUserInfoDao;
	@Override
	public String deleteAllLoanByLoginname(String loginname) {

		return fengdaiUserInfoDao.deleteAllLoanByLoginname(loginname);


	}
	@Override
	public String deleteAllLoanWithoutCreditByLoginname(String loginname) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteUserByLoginname(String loginname) {

		return fengdaiUserInfoDao.deleteUserByLoginname(loginname);
	}
	@Override
	public String deleteLoanByLoanName(String loanname) {

		return fengdaiUserInfoDao.deleteLoanByLoanName(loanname);
	}
	@Override
	public String deleteLoanByLoanId(String loanapplyid) {

		return fengdaiUserInfoDao.deleteLoanByLoanId(loanapplyid);
	}
	@Override
	public String changeSQDToLoanning(String loanname) {
		return fengdaiUserInfoDao.changeSQDToLoanning(loanname);
	}
	@Override
	public String changeProcessSQDToLoanning(String loanname) {
		return fengdaiUserInfoDao.changeProcessSQDToLoanning(loanname);
	}
	@Override
	public String changeUserAccount(String username, BigDecimal moneynum) {
		return fengdaiUserInfoDao.changeUserAccount(username, moneynum);
	}



}
