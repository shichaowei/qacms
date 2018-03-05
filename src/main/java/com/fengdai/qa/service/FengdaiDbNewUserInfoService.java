package com.fengdai.qa.service;

import java.math.BigDecimal;

public interface FengdaiDbNewUserInfoService {

	public  String deleteAllLoanByLoginname(String loginname) ;
	public  String deleteAllLoanWithoutCreditByLoginname(String loginname) ;
	public  String deleteUserByLoginname(String loginname) ;
	public  String deleteLoanByLoanName(String loanname) ;
	public  String deleteLoanByLoanId(String loanid) ;
	public String changeSQDToLoanning(String loanname);
	public String changeProcessSQDToLoanning(String loanname);
	public String changeUserAccount(String username,BigDecimal money);



}
