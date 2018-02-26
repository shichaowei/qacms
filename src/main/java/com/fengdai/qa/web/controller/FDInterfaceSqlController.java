package com.fengdai.qa.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author hzweisc
 *
 */

import com.fengdai.qa.meta.FDSqlInfo;
import com.fengdai.qa.service.FengdaiSqlService;
import com.fengdai.qa.utils.LocalDateTimeUtils;
@Controller
public class FDInterfaceSqlController {


	@Autowired
	public FengdaiSqlService fengdaiSqlServiceImpl;

	@RequestMapping({ "sqlprocess" })
	public void getIndex(FDSqlInfo fdSqlInfo,HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		System.out.println(fdSqlInfo.getSql().replaceAll("\n", " "));
		fengdaiSqlServiceImpl.addSql(fdSqlInfo);
		System.out.println(LocalDateTimeUtils.formatTime(LocalDateTimeUtils.convertDateToLDT(fengdaiSqlServiceImpl.getDbtime()), "yyyy-MM-dd HH:mm:ss"));


	}

}
