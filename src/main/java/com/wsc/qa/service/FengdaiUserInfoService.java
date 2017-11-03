package com.wsc.qa.service;

import java.math.BigDecimal;

public interface FengdaiUserInfoService {

	public  void deleteAllLoanByLoginname(String loginname) ;
	public  void deleteAllLoanWithoutCreditByLoginname(String loginname) ;
	public  void deleteUserByLoginname(String loginname) ;
	public  void deleteLoanByLoanName(String loanname) ;
	public  void deleteLoanByLoanId(String loanid) ;
	public void changeSQDToLoanning(String loanname);
	public void changeProcessSQDToLoanning(String loanname);
	public void changeUserAccount(String username,BigDecimal money);



}
