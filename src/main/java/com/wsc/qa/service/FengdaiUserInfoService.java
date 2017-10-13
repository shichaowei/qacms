package com.wsc.qa.service;

public interface FengdaiUserInfoService {

	public  void deleteAllLoanByLoginname(String loginname) ;
	public  void deleteAllLoanWithoutCreditByLoginname(String loginname) ;
	public  void deleteUserByLoginname(String loginname) ;
	public  void deleteLoanByLoanName(String loanname) ;
	public  void deleteLoanByLoanId(String loanid) ;
	public void changeSQDToLoanning(String loanname);
	public void changeProcessSQDToLoanning(String loanname);



}
