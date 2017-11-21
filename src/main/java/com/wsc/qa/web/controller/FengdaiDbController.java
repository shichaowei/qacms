package com.wsc.qa.web.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.constants.CommonConstants.deleteCode;
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.DbInfo.callbackUrlCode;
import com.wsc.qa.datasource.DataSourceContextHolder;
import com.wsc.qa.datasource.DataSourceInfo;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.service.CreateCallbackService;
import com.wsc.qa.service.FengdaiCallbakInfoService;
import com.wsc.qa.service.FengdaiService;
import com.wsc.qa.service.FengdaiUserInfoService;
import com.wsc.qa.utils.JsonFormatUtil;
import com.wsc.qa.utils.OkHttpUtil;

@Controller
public class FengdaiDbController {

	private static final Logger logger = LoggerFactory.getLogger(FengdaiDbController.class);

	@Autowired
	private FengdaiUserInfoService fengdaiUserInfoServiceImpl;
	@Autowired
	private FengdaiService fengdaiServiceImpl;
	@Autowired
	private FengdaiCallbakInfoService fengdaiCallbakInfoServiceImpl;
	@Autowired
	private CreateCallbackService createCallbackServiceImpl;



	/**
	 * remark字段生成回调报文
	 *
	 * @param remark
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping({ "createCallbackStr" })
	@OperaLogComment(remark = opertype.fundscallbackfengdai)
	public String createCallbackStr(@RequestParam("callbackEnv") String callbackEnv, @RequestParam("type") String type,
			@RequestParam("fieldDetail") String fieldDetail, HttpServletRequest request, ModelMap map,HttpServletResponse response) {
		/**
		 * 拿到remark字段
		 */
		String remark = "";
		if (CommonConstants.callbackType.virRelateId.getValue().equals(type)) {
			// 切换数据库
			if("fengdaiold".equals(callbackEnv)) {
				DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_TESTDB);
				remark = fengdaiServiceImpl.getremark(fieldDetail);
			}else if ("fengdainew".equals(callbackEnv)) {
				DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_TESTDBNEW);
				remark = fengdaiServiceImpl.getremarkNew(fieldDetail);
			}

		} else if (CommonConstants.callbackType.virRemark.getValue().equals(type)) {
			remark = fieldDetail;
		} else {
			throw new BusinessException(ErrorCode.ERROR_PARAMS_INVALIED, "参数非法");
		}
		/**
		 * 开始发送报文
		 */
		String callbackStr = createCallbackServiceImpl.genCallbackStr(remark);
		logger.info("生成的callback的body:{}",callbackStr);
		try {
			if("fengdaiold".equals(callbackEnv)) {
				OkHttpUtil.post(callbackUrlCode.callbackold.getValue(), callbackStr);
			}else if ("fengdainew".equals(callbackEnv)) {
				OkHttpUtil.post(callbackUrlCode.callbacknew.getValue(), callbackStr);
			}

		} catch (IOException e) {
			throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("post请求失败"), e);
		}
		map.addAttribute("callbackStr", JsonFormatUtil.jsonFormatter(callbackStr));
		return "display";
	}




	/**
	 *
	 * @param deleteType
	 * @param param
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping({ "deleteUserInfo" })
	@OperaLogComment(remark = opertype.deletefengdaidata)
	public String deleteUserInfo(String deleteMode,String deleteType, String param, String moneynumStr,HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {

		if("OLD".equals(deleteMode)) {
			// 切换数据库
			DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_TESTDB);
		}else if ("NEW".equals(deleteMode)) {
			DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_TESTDBNEW);
		}

		switch (deleteCode.valueOf(deleteType)) {
		case deleteAllLoanByLoginname:
			fengdaiUserInfoServiceImpl.deleteAllLoanByLoginname(param);
			map.addAttribute("resultmsg", "删除用户申请单与资金流水所有数据");
			break;
		case deleteUserByLoginname:
			fengdaiUserInfoServiceImpl.deleteUserByLoginname(param);
			map.addAttribute("resultmsg", "从数据库删除整个用户");
			break;
		case deleteLoanByLoanName:
			fengdaiUserInfoServiceImpl.deleteLoanByLoanName(param);
			map.addAttribute("resultmsg", "根据借款名称删除指定的申请单");
			break;
		case deleteLoanByLoanId:
			fengdaiUserInfoServiceImpl.deleteLoanByLoanId(param);
			map.addAttribute("resultmsg", "根据借款申请id删除指定的申请单");
			break;
		case changeSQDToLoanning:
			fengdaiUserInfoServiceImpl.changeSQDToLoanning(param);
			map.addAttribute("resultmsg", "修改申请单为待放款，绕开签约");
			break;
		case changeProcessSQDToLoanning:
			fengdaiUserInfoServiceImpl.changeProcessSQDToLoanning(param);
			map.addAttribute("resultmsg", "修改申请单为待放款，处理放款中无法再放款");
			break;
		case changeUserAmount:

				String username = param;
				//构造以字符串内容为值的BigDecimal类型的变量bd
				BigDecimal moneynum=new BigDecimal(moneynumStr);
				//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
				moneynum=moneynum.setScale(2, BigDecimal.ROUND_HALF_UP);
				fengdaiUserInfoServiceImpl.changeUserAccount(username, moneynum);
				map.addAttribute("resultmsg", "修改用户:"+param+";金额为:"+moneynumStr);

			break;
		default:
			map.addAttribute("resultmsg", "没有匹配到任何操作");
			break;
		}
		// 切换数据库
		DataSourceContextHolder.setDbType(DataSourceInfo.SOURCE_ADMIN);
		return "display";
	}


}
