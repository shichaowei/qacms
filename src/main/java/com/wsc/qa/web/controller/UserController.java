package com.wsc.qa.web.controller;

import java.io.IOException;

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
import com.wsc.qa.constants.IndexNav;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.meta.User;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.CreateCallbackService;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.utils.ForMatJSONUtil;
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
			map.addAttribute("userName", userName);
			// map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
			// map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
			// map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
			return "index";
		} else {
			return "login";
		}
	}
	


	@RequestMapping({ "index" })
	public String getIndexInfo(@RequestParam("item") String item, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (userName != null && userName.equals(userName)) {
			
			if(item.equals("createCallbackStr"))
				map.addAttribute("item", IndexNav.createCallbackStr);
			if(item.equals("changetime"))
				map.addAttribute("item", IndexNav.changetime);
			if(item.equals("fixenv"))
				map.addAttribute("item", IndexNav.fixenv);
			if(item.equals("mock"))
				map.addAttribute("item", IndexNav.mock);
			if(item.equals("logout")){
				session.invalidate();
				return "login";
			}
			if(item.equals("login")){
				return "login";
			}
			map.addAttribute("userName", userName);
			
			return "index";
		} else {
			return "login";
		}

	}

	@RequestMapping({ "/api/login" })
	@Log(operationType = "login操作:", operationName = "登录")
	public String login(@ModelAttribute @Valid User userRe,
			HttpServletResponse response, HttpServletRequest request,ModelMap map,Error errors) throws IOException, BusinessException {
		String userName=userRe.getUserName();
		String userPassword = userRe.getUserPassword();
		logger.logInfo("username is:" + userName);
		User user = userServiceImpl.getUserInfo(userName);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				HttpSession session = request.getSession();
				Cookie userNameCookie = new Cookie("userName", userName);
				Cookie pwdCookie = new Cookie("pwd", userPassword);
				userNameCookie.setMaxAge(10 * 60);
				pwdCookie.setMaxAge(10 * 60);

				session.setAttribute("userName", userName);
				response.addCookie(userNameCookie);
				response.addCookie(pwdCookie);
//				response.sendRedirect("user/" + userName);
				map.addAttribute("userName", user.getUserName());
			} else {
//				throw new BusinessException("sfsdfsdfd");
				throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
			}

		} else {
//			throw new BusinessException("sfsdfsdfd");
//			response.sendRedirect("user/error");
			throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
		}
		return "index";
	}

//	@RequestMapping({ "user/{userName}" })
//	@Log(operationType = "getInfo操作:", operationName = "获取用户信息")
//	public String getInfo(@PathVariable("userName") String userNameRe, HttpServletRequest request, ModelMap map,
//			HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		String userName = (String) session.getAttribute("userName");
//		if (userName != null && userName.equals(userNameRe)) {
//			map.addAttribute("userName", userNameRe);
//			// map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
//			// map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
//			// map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
//			return "index";
//		} else {
//			return "error";
//		}
//	}

	@RequestMapping({ "fixenv" })
	public String fixenv(@RequestParam("zkAddress") String zkAddress, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		dealEnvServiceImpl.fixenv(zkAddress);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		map.addAttribute("userName", userName);
		// map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
		// map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
		// map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
		// 插入最後操作的數據
		operaLogServiceImpl.insertOperLog(userName, "fixenv");
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
	public String createCallbackStr(@RequestParam("remark") String remark, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		String callbackStr = createCallbackServiceImpl.genCallbackStr(remark);
		System.out.println(callbackStr);
		map.addAttribute("callbackStr", ForMatJSONUtil.format(callbackStr));
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
	public String mockMessage(@RequestParam("mockserverip") String mockserverip,
			@RequestParam("ContentType") String ContentType, @RequestParam("checkUrl") String checkUrl,
			@RequestParam("checkPostParams") String checkPostParams,
			@RequestParam("checkGetParams") String checkGetParams, @RequestParam("responseBody") String responseBody,
			HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		List<String> checkPostParamsList = new ArrayList<>();
		List<String> checkGetParamsList = new ArrayList<>();
		if (checkPostParams != null && !checkPostParams.isEmpty() && !checkPostParams.equals("checkPostParams")) {
			checkPostParamsList = java.util.Arrays.asList(checkPostParams.split(";"));
		} else {
			checkPostParams = null;
		}
		if (checkGetParams != null && !checkGetParams.isEmpty() && !checkGetParams.equals("checkGetParams")) {
			checkGetParamsList = java.util.Arrays.asList(checkGetParams.split(";"));
		}
		String mockRule = mockMessServiceImpl.mockProcess(mockserverip, ContentType, responseBody, checkUrl,
				checkPostParamsList, checkGetParamsList);
		map.addAttribute("mockRuleStr", ForMatJSONUtil.format(mockRule));
		return "display";
	}

	@RequestMapping({ "changetime" })
	public String changetime(@RequestParam("changetimetype") String changetimetype, @RequestParam("date") String date,
			@RequestParam("time") String time, HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		String cmd = "date -s '" + date + " " + time + "'";
		switch (changetimetype) {

		case "changeDubbotime": {
			String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243" };

			changeTimeImpl.changeServerTime(ipaddress, cmd);
			break;
		}
		case "changDubboDbtime": {
			String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.249.242" };
			changeTimeImpl.changeServerTime(ipaddress, cmd);
			break;
		}
		case "changDubboResttime": {
			String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.250.25",
					"172.30.248.218", "172.30.251.190" };
			changeTimeImpl.changeServerTime(ipaddress, cmd);
			break;
		}
		case "changDubboRestDbtime": {
			String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243", "172.30.250.25",
					"172.30.248.218", "172.30.251.190", "172.30.249.242" };
			changeTimeImpl.changeServerTime(ipaddress, cmd);
			break;
		}
		default:
			break;
		}
		// 插入最後操作的數據
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		operaLogServiceImpl.insertOperLog(userName, changetimetype);

		map.addAttribute("userName", userName);
		// map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
		// map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
		// map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
		return "index";

	}

}
