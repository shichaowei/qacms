package com.wsc.qa.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
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
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.IndexNav;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.meta.CallbackInfo;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.meta.User;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.FengdaiCallbakInfoService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.utils.GetUserUtil;
/**
 *
 *
 *
 * @author hzweisc
 *
 */
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userServiceImpl;


	@Autowired
	private OperLogService operaLogServiceImpl;
	@Autowired
	private DealEnvService dealEnvServiceImpl;

	@Autowired
	private MockMessService mockMessServiceImpl;

	@Autowired
	private FengdaiCallbakInfoService fengdaiCallbakInfoServiceImpl;

	Lock mocklock = new ReentrantLock();



	@RequestMapping({ "/" })
	@Log(operationType = "首页操作:", operationName = "首页")
	public String getIndex(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (null != userName && !userName.isEmpty()) {
			// map.addAttribute("userName", userName);
			// map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
			return "index";
		} else {
			return "login";
		}
	}

	@RequestMapping({ "/register" })
	@Log(operationType = "操作:", operationName = "注册操作：")
	public String register(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		return "register";
	}

	@RequestMapping({ "index" })
	@Log(operationType = "操作:", operationName = "点击目录操作")
	public String getIndexInfo(@RequestParam("item") String item, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if (null != userName && userName.equals(userName)) {
			switch (item) {
			case "createCallbackStr":
				map.addAttribute("item", IndexNav.createCallbackStr);
				break;
			case "changetime":
				map.addAttribute("item", IndexNav.changetime);
				break;
			case "newchangetime":
				map.addAttribute("item", IndexNav.newchangetime);
				break;

			case "addmockrule":
				map.addAttribute("item", IndexNav.addmockrule);
				break;
			case "displaymockrules":{
				List<MockInfo> var = mockMessServiceImpl.getAllMockInfos();
				map.addAttribute("mockrules",var);
				return "mockInfoDis";
			}
			case "gethttpinterface":
				map.addAttribute("item", IndexNav.gethttpinterface);
				break;
			case "fixenv":
				map.addAttribute("item", IndexNav.fixenv);
				break;
			case "deleteUserInfo":
				map.addAttribute("item", IndexNav.deleteUserInfo);
				break;
			case "login":
				return "login";
			case "getcallbackInfo":
			{
				List<CallbackInfo> callbackinfolist= fengdaiCallbakInfoServiceImpl.getAllCallbakInfo(0, 10);
				map.addAttribute("callbackinfolist",callbackinfolist);
				return "display";
			}
			case "logout":
				session.invalidate();
				return "login";
			default:
				break;
			}
			// map.addAttribute("userName", userName);
			// map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());

			return "index";
		} else {
			return "login";
		}

	}

	@RequestMapping({ "/api/login" })
	// @OperaLogComment(remark="login")
	@Log(operationType = "login的api操作:", operationName = "登录")
	public String login(@ModelAttribute @Valid User userRe, HttpServletResponse response, HttpServletRequest request,
			ModelMap map, Error errors) throws IOException, BusinessException {
		String userName = userRe.getUserName();
		String userPassword = userRe.getUserPassword();
		logger.info("username is:" + userName);
		User user = userServiceImpl.getUserInfo(userName);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				HttpSession session = request.getSession();
				// cookie取中文需要 URLEncoder.encode
				Cookie userNameCookie = new Cookie("userName", URLEncoder.encode(user.getUserName(), "utf-8"));
				Cookie pwdCookie = new Cookie("pwd", userPassword);
				userNameCookie.setMaxAge(365*24*3600);
				pwdCookie.setMaxAge(365*24*3600);
				session.setAttribute("userName", userName);
				response.addCookie(userNameCookie);
				response.addCookie(pwdCookie);
				// response.sendRedirect("user/" + userName);
				// map.addAttribute("userName", user.getUserName());
			} else {
				throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
			}

		} else {
			throw new BusinessException(ErrorCode.ERROR_ACCOUTWRONG);
		}
		// map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		return "index";
	}

	/**
	 * 注册
	 *
	 * @param user
	 * @param response
	 * @param request
	 * @param map
	 * @param errors
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws BusinessException
	 * @throws FileUploadException
	 */
	@RequestMapping({ "/api/register" })
	@Log(operationType = "register的api操作:", operationName = "注册")
	/** @OperaLogComment(remark="register")**/
	public String registerApi( User user,HttpServletResponse response,
			HttpServletRequest request, ModelMap map, Error errors) throws UnsupportedEncodingException {
		if(userServiceImpl.getUserInfo(user.getUserName()) == null) {
			userServiceImpl.insertUserInfo(user);
			String userName = URLEncoder.encode(user.getUserName(), "utf-8");

			String userPassword = user.getUserPassword();
			HttpSession session = request.getSession();
			Cookie userNameCookie = new Cookie("userName", userName);
			Cookie pwdCookie = new Cookie("pwd", userPassword);
			userNameCookie.setMaxAge(60 * 60);
			pwdCookie.setMaxAge(60 * 60);
			session.setAttribute("userName", userName);
			response.addCookie(userNameCookie);
			response.addCookie(pwdCookie);
			map.addAttribute("userName", GetUserUtil.getUserName(request));
			return "index";
		}else {
			throw new BusinessException(ErrorCode.ERROR_ILLEGAL_USERNAME);
		}
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
	@OperaLogComment(remark = opertype.fixfengdaienv)
	@Log(operationType = "修复环境操作:", operationName = "修复环境")
	public String fixenv(@RequestParam("zkAddress") String zkAddress, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		dealEnvServiceImpl.fixenv(zkAddress);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		// map.addAttribute("userName", userName);
		// map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		// 插入最後操作的數據
		// operaLogServiceImpl.insertOperLog(userName, "fixenv");
		return "index";
	}





	// @RequestMapping("/test")
	// public String hehe(HttpSession session,ModelMap map,HttpServletRequest
	// request) {
	// session.setAttribute("haha", "nini");
	// map.addAttribute("haha", "map1");
	// request.setAttribute("haha", "request");
	// request.setAttribute("reqqqqqq", request);
	// return "hehe";
	// }
}
