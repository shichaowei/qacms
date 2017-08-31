package com.wsc.qa.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsc.qa.annotation.Log;
import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.IndexNav;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.meta.OperaLog;
import com.wsc.qa.meta.User;
import com.wsc.qa.mockmode.modecheckParamsStr;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.CreateCallbackService;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.utils.ForMatJSONUtil;
import com.wsc.qa.utils.GetUserUtil;
import com.wsc.qa.utils.LogUtil;
import com.wsc.qa.constants.CommonConstants.ErrorCode;

@Controller
public class UserController {
	final LogUtil logger = new LogUtil(this.getClass());

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
	@Log(operationType = "login的api操作:", operationName = "登录")
	public String login(@ModelAttribute @Valid User userRe,
			HttpServletResponse response, HttpServletRequest request,ModelMap map,Error errors) throws IOException, BusinessException {
		String userName=userRe.getUserName();
		String userPassword = userRe.getUserPassword();
		logger.logInfo("username is:" + userName);
		User user = userServiceImpl.getUserInfo(userName);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				HttpSession session = request.getSession();
				//cookie取中文需要 URLEncoder.encode 
				Cookie userNameCookie = new Cookie("userName", URLEncoder.encode (user.getUserName(),"utf-8"));
				Cookie pwdCookie = new Cookie("pwd", userPassword);
				userNameCookie.setMaxAge(10 * 60);
				pwdCookie.setMaxAge(10 * 60);
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
	
	@RequestMapping({ "/api/register" })
	@Log(operationType = "register的api操作:", operationName = "注册")
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
	 */
	@RequestMapping({ "createCallbackStr" })
	@OperaLogComment(remark="remark字段生成回调报文")
	public String createCallbackStr(@RequestParam("remark") String remark, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		String callbackStr = createCallbackServiceImpl.genCallbackStr(remark);
		map.addAttribute("callbackStr", ForMatJSONUtil.format(callbackStr));
//		map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		return "display";
	}

	/**
	 * 根據要mock的數據，在指定的服務器上啟動anyproxy，mock相關數據 checkPostParams checkGetParams
	 * 以分隔符；為單位
	 * 
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
		map.addAttribute("mockRuleStr", ForMatJSONUtil.format(mockRule));
		return "display";
	}

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
		default:
			break;
		}
		map.addAttribute("servernowtime", date+" "+time);
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
