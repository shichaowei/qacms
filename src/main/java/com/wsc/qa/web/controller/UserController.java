package com.wsc.qa.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsc.qa.annotation.Log;
import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.constants.CommonConstants.deleteCode;
import com.wsc.qa.constants.IndexNav;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.datasource.DataSourceContextHolder;
import com.wsc.qa.datasource.DataSourceType;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.exception.MyExceptionHandler;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.meta.User;
import com.wsc.qa.service.ActivityBannerService;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.CreateCallbackService;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.FengdaiUserInfoService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.utils.GetNetworkTimeUtil;
import com.wsc.qa.utils.GetUserUtil;
import com.wsc.qa.utils.JsonFormatUtil;
import com.wsc.qa.utils.OkHttpUtil;
import com.wsc.qa.utils.SmilarJSONFormatUtil;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Autowired
	private UserService userServiceImpl;

	@Resource
	private ChangeTimeService changeTimeImpl;
	@Autowired
	private OperLogService operaLogServiceImpl;
	@Autowired
	private DealEnvService dealEnvServiceImpl;
	@Autowired
	private CreateCallbackService createCallbackServiceImpl;
	@Autowired
	private MockMessService mockMessServiceImpl;
	@Autowired
	private ActivityBannerService activityBannerServiceImpl;
	@Autowired
	private FengdaiUserInfoService fengdaiUserInfoServiceImpl;

	@RequestMapping({ "/" })
	@Log(operationType = "首页操作:", operationName = "首页")
	public String getIndex(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (null != userName && !userName.isEmpty()) {
//			map.addAttribute("userName", userName);
//			map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
			return "index";
		} else {
			return "login";
		}
	}

	@RequestMapping({ "/register" })
	@Log(operationType = "注册操作:", operationName = "注册")
	public String register(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		return "register";
	}



	@RequestMapping({ "index" })
	@Log(operationType = "目录操作:", operationName = "目录")
	public String getIndexInfo(@RequestParam("item") String item, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (null !=userName  && userName.equals(userName)) {
			switch (item) {
			case "createCallbackStr":
				map.addAttribute("item", IndexNav.createCallbackStr);
				break;
			case "changetime":
				map.addAttribute("item", IndexNav.changetime);
				break;

			case "mock":
				map.addAttribute("item", IndexNav.mock);
				break;
			case "fixenv":
				map.addAttribute("item", IndexNav.fixenv);
				break;
			case "deleteUserInfo":
				map.addAttribute("item", IndexNav.deleteUserInfo);
				break;
			case "login":
				return "login";
			case "logout":
				session.invalidate();
				return "login";
			default:
				break;
			}
//			map.addAttribute("userName", userName);
//			map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());


			return "index";
		} else {
			return "login";
		}

	}

	@RequestMapping({ "/api/login" })
//	@OperaLogComment(remark="login")
	@Log(operationType = "login的api操作:", operationName = "登录")
	public String login(@ModelAttribute @Valid User userRe,
			HttpServletResponse response, HttpServletRequest request,ModelMap map,Error errors) throws IOException, BusinessException {
		String userName=userRe.getUserName();
		String userPassword = userRe.getUserPassword();
		logger.info("username is:" + userName);
		User user = userServiceImpl.getUserInfo(userName);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				HttpSession session = request.getSession();
				//cookie取中文需要 URLEncoder.encode
				Cookie userNameCookie = new Cookie("userName", URLEncoder.encode (user.getUserName(),"utf-8"));
				Cookie pwdCookie = new Cookie("pwd", userPassword);
				userNameCookie.setMaxAge(1 * 10);
				pwdCookie.setMaxAge(1 * 10);
				session.setAttribute("userName", userName);
				response.addCookie(userNameCookie);
				response.addCookie(pwdCookie);
//				response.sendRedirect("user/" + userName);
//				map.addAttribute("userName", user.getUserName());
			} else {
				throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
			}

		} else {

			throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
		}
//		map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		return "index";
	}

	/**
	 * 注册
	 * @param user
	 * @param response
	 * @param request
	 * @param map
	 * @param errors
	 * @return
	 * @throws IOException
	 * @throws BusinessException
	 */
	@RequestMapping({ "/api/register" })
	@Log(operationType = "register的api操作:", operationName = "注册")
//	@OperaLogComment(remark="register")
	public String registerApi(@ModelAttribute @Valid User user,
			HttpServletResponse response, HttpServletRequest request,ModelMap map,Error errors) throws IOException, BusinessException {
		userServiceImpl.insertUserInfo(user);
		String userName=URLEncoder.encode (user.getUserName(),"utf-8");
		String userPassword = user.getUserPassword();
		HttpSession session = request.getSession();
		Cookie userNameCookie = new Cookie("userName", userName);
		Cookie pwdCookie = new Cookie("pwd", userPassword);
		userNameCookie.setMaxAge(10 * 60);
		pwdCookie.setMaxAge(10 * 60);
		session.setAttribute("userName", userName);
		response.addCookie(userNameCookie);
		response.addCookie(pwdCookie);
		map.addAttribute("userName",GetUserUtil.getUserName(request));
		return "index";
	}


	/**
	 *
	 *
	 * @param zkAddress
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping({ "fixenv" })
	@OperaLogComment(remark="fixenv")
	@Log(operationType = "修复环境操作:", operationName = "修复环境")
	public String fixenv(@RequestParam("zkAddress") String zkAddress, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		dealEnvServiceImpl.fixenv(zkAddress);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
//		map.addAttribute("userName", userName);
//		map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		// 插入最後操作的數據
//		operaLogServiceImpl.insertOperLog(userName, "fixenv");
		return "index";
	}

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
	@OperaLogComment(remark="remark字段生成回调报文")
	public String createCallbackStr(@RequestParam("callbackUrl") String callbackUrl,@RequestParam("type") String type,
			@RequestParam("fieldDetail") String fieldDetail, HttpServletRequest request, ModelMap map,
			HttpServletResponse response)   {
		String remark="";
		if(type.equals("virRelateId")) {
			String sqlMode ="select remarks FROM fengdai_mqnotify.mc_business WHERE relate_id ='%s' ORDER BY create_date DESC ";
			remark = new com.tairan.framework.utils.DBUtil("fengdai").executeQueryGetMap(String.format(sqlMode, fieldDetail)).get("remarks").get(0);
		}else if (type.equals("virRemark")) {
			remark = fieldDetail;
		}else {
			throw new BusinessException(ErrorCode.ERROR_PARAMS_INVALIED, "参数非法");
		}
		String callbackStr = createCallbackServiceImpl.genCallbackStr(remark);
		map.addAttribute("callbackStr", JsonFormatUtil.jsonFormatter(callbackStr));
//		map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		try {
			OkHttpUtil.post(callbackUrl, callbackStr);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("post请求失败"), e);
		}

		return "display";
	}

	/**
	 * 根據要mock的數據，在指定的服務器上啟動anyproxy，mock相關數據 checkPostParams checkGetParams
	 * 以分隔符；為單位
	 * test
	 * curl -d "{"name" : "魏士超"}" http://172.30.251.176/credit-thirdparty-rest/api/auto/fill/factor --proxy http://127.0.0.1:8001
	 * @param mockserverip
	 * @param ContentType
	 * @param checkUrl
	 * @param checkPostParams
	 * @param checkGetParams
	 * @param responseBody
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping({ "mockMessage" })
	@OperaLogComment(remark="mock數據")
	public String mockMessage(MockInfo mockinfo,
			HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		String mockRule = mockMessServiceImpl.mockProcess(mockinfo);
		map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));

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
	@OperaLogComment(remark="删除用户数据")
	public String deleteUserInfo(String deleteType, String param,
			HttpServletRequest request, ModelMap map, HttpServletResponse response) {

		//切换数据库
		DataSourceContextHolder.setDbType(DataSourceType.SOURCE_TESTDB);
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
			default:
				map.addAttribute("resultmsg", "没有匹配到任何操作");
				break;
		}
		//切换数据库
		DataSourceContextHolder.setDbType(DataSourceType.SOURCE_ADMIN);
		return "display";
	}


	/**
	 * 修改时间
	 * @param changetimetype
	 * @param date
	 * @param time
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping({ "changetime" })
	@OperaLogComment(remark="修改時間")
	public String changetime(@RequestParam("changetimetype") String changetimetype, @RequestParam("date") String date,
			@RequestParam("time") String time, HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		String cmd = "date -s '" + date + " " + time + "'";
		switch (changetimetype) {

			case "changeDubbotime": {
				changeTimeImpl.changeServerTime(ServerInfo.changeDubbotimeIps, cmd);
				break;
			}
			case "changDubboDbtime": {
				changeTimeImpl.changeServerTime(ServerInfo.changDubboDbtimeIps, cmd);
				break;
			}
			case "changDubboResttime": {
				changeTimeImpl.changeServerTime(ServerInfo.changDubboResttimeIps, cmd);
				break;
			}
			case "changDubboRestDbtime": {
				changeTimeImpl.changeServerTime(ServerInfo.changDubboRestDbtimeIps, cmd);
				break;
			}
			case "restAllTime": {
				cmd = "date -s '"+GetNetworkTimeUtil.getWebsiteDatetime()+"'";
				changeTimeImpl.changeServerTime(ServerInfo.changDubboRestDbtimeIps, cmd);
				break;
			}
			default:
				break;
		}
		map.addAttribute("servernowtime", cmd);
		return "display";
	}


//	@RequestMapping("/test")
//	public String hehe(HttpSession session,ModelMap map,HttpServletRequest request) {
//		session.setAttribute("haha", "nini");
//		map.addAttribute("haha", "map1");
//		request.setAttribute("haha", "request");
//		request.setAttribute("reqqqqqq", request);
//		return "hehe";
//	}
}
