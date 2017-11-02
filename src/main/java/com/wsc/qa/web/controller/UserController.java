package com.wsc.qa.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
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
import com.wsc.qa.constants.CommonConstants;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.constants.CommonConstants.deleteCode;
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.IndexNav;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.datasource.DataSourceContextHolder;
import com.wsc.qa.datasource.DataSourceType;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.exception.MyExceptionHandler;
import com.wsc.qa.meta.CallbackInfo;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.meta.User;
import com.wsc.qa.service.ActivityBannerService;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.CreateCallbackService;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.FengdaiCallbakInfoService;
import com.wsc.qa.service.FengdaiService;
import com.wsc.qa.service.FengdaiUserInfoService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.utils.FormateDateUtil;
import com.wsc.qa.utils.GetNetworkTimeUtil;
import com.wsc.qa.utils.GetUserUtil;
import com.wsc.qa.utils.JsonFormatUtil;
import com.wsc.qa.utils.OkHttpUtil;
import com.wsc.qa.utils.SmilarJSONFormatUtil;
/**
 *
 *
 *
 * @author hzweisc
 *
 */
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
	@Autowired
	private FengdaiService fengdaiServiceImpl;
	@Autowired
	private FengdaiCallbakInfoService fengdaiCallbakInfoServiceImpl;
	/**修改时间需要用到锁，同一时间只有一个人在修改，如果在方法中的lock变量是局部变量，每个线程执行该方法时都会保存一个副本，
	那么每个线程执行到lock.lock()处获取的是不同的锁，所以就不会对临界资源形成同步互斥访问。因此，我们只需要将lock声明为成员变量即可
	**/
	Lock changetimelock = new ReentrantLock();
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
				userNameCookie.setMaxAge(1 * 10);
				pwdCookie.setMaxAge(1 * 10);
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
	 *用来提供回调地址，用于mock第三方服务器，并展示出服务器拿到的东西
	 * @param response
	 * @param request
	 * @param map
	 * @param errors
	 * @throws FileUploadException
	 * @throws IOException
	 */
	@RequestMapping({"callbackloop"})
	public void callbackloop(HttpServletResponse response,
			HttpServletRequest request, ModelMap map, Error errors) throws FileUploadException, IOException {

		CallbackInfo callbackInfo = new CallbackInfo();
		String mode = request.getMethod();
		switch (mode) {
			case "POST":{
				if("application/x-www-form-urlencoded".equals(request.getContentType())) {
					StringBuffer callbackBuffer = new StringBuffer();
					callbackBuffer.append("application/x-www-form-urlencoded表单提交----");
					Enumeration<?> names2 = request.getParameterNames();
			        while(names2.hasMoreElements()){
			        	String var = names2.nextElement().toString();
			            callbackBuffer.append(var+":"+request.getParameter(var)+";");
			        }
			        callbackInfo.setCallbackinfo(callbackBuffer.toString());
			        callbackInfo.setRequestip(request.getRemoteHost());

				}else if (request.getContentType() != null&&request.getContentType().contains("multipart/form-data")&&request.getContentType().contains("boundary")) {
					StringBuffer callbackBuffer = new StringBuffer();
					callbackBuffer.append("multipart/form-data提交----");
					DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
					ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
					fileUpload.setHeaderEncoding("UTF-8");
					List<FileItem> list = fileUpload.parseRequest(request);
					for (FileItem item : list) {
						//如果fileitem中封装的是普通输入项的数据
		                if(item.isFormField()){
		                	String name = item.getFieldName();
		                    //解决普通输入项的数据的中文乱码问题
		                    String value = item.getString("UTF-8");
//		                    System.out.println(name);
//		                    System.out.println(value);
		                    callbackBuffer.append(name+":"+value+";");
		                }else {
		                	String fileName = item.getName();
		                	int index = fileName.lastIndexOf("\\");
		                	fileName = fileName.substring(index + 1);
//		                	System.out.println(fileName);
		                	callbackBuffer.append(fileName);
						}
					}
					callbackInfo.setCallbackinfo(callbackBuffer.toString());
			        callbackInfo.setRequestip(request.getRemoteHost());

				}else if ("application/json".equals(request.getContentType())) {
					StringBuffer callbackBuffer = new StringBuffer();
					callbackBuffer.append("application/json提交----");
					BufferedReader br=request.getReader();
					String data = null;
					while((data = br.readLine())!=null)
					{
//						System.out.println(data);
						callbackBuffer.append(data+"\n");
					}
					callbackInfo.setCallbackinfo(callbackBuffer.toString());
			        callbackInfo.setRequestip(request.getRemoteHost());
				}else {
					logger.error("content is error:{}",request.getContentType());
					throw new BusinessException(ErrorCode.ERROR_ILLEGAL_REQUEST);
				}
				break;
			}
			case "GET":
				StringBuffer callbackBuffer = new StringBuffer();
				callbackBuffer.append("get提交----");
//				System.out.println(request.getQueryString());
				callbackBuffer.append(request.getQueryString());
				callbackInfo.setCallbackinfo(callbackBuffer.toString());
		        callbackInfo.setRequestip(request.getRemoteHost());
				break;
			default:
				break;
		}
		callbackInfo.setCreatetime(FormateDateUtil.format(Calendar.getInstance().getTime()));
		fengdaiCallbakInfoServiceImpl.addCallbackInfo(callbackInfo);

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
	public String createCallbackStr(@RequestParam("callbackUrl") String callbackUrl, @RequestParam("type") String type,
			@RequestParam("fieldDetail") String fieldDetail, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		String remark = "";
		if (CommonConstants.callbackType.virRelateId.getValue().equals(type)) {
/**			String sqlMode = "select remarks FROM fengdai_mqnotify.mc_business WHERE relate_id ='%s' ORDER BY create_date DESC ";
			remark = new com.tairan.framework.utils.DBUtil("fengdai")
					.executeQueryGetMap(String.format(sqlMode, fieldDetail)).get("remarks").get(0);
					**/
			// 切换数据库
			DataSourceContextHolder.setDbType(DataSourceType.SOURCE_TESTDB);
			remark = fengdaiServiceImpl.getremark(fieldDetail);
		} else if (CommonConstants.callbackType.virRemark.getValue().equals(type)) {
			remark = fieldDetail;
		} else {
			throw new BusinessException(ErrorCode.ERROR_PARAMS_INVALIED, "参数非法");
		}
		String callbackStr = createCallbackServiceImpl.genCallbackStr(remark);
		map.addAttribute("callbackStr", JsonFormatUtil.jsonFormatter(callbackStr));
		// map.addAttribute("lastoperaInfo",operaLogServiceImpl.getLastOper());
		try {
			OkHttpUtil.post(callbackUrl, callbackStr);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.ERROR_OTHER_MSG.customDescription("post请求失败"), e);
		}

		return "display";
	}

	/**
	 * 根據要mock的數據，在指定的服務器上啟動anyproxy，mock相關數據 checkPostParams checkGetParams
	 * 以分隔符；為單位 test curl -d "{"name" : "魏士超"}"
	 * http://172.30.251.176/credit-thirdparty-rest/api/auto/fill/factor --proxy
	 * http://127.0.0.1:8001
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
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@RequestMapping({ "mockMessage" })
	@OperaLogComment(remark = opertype.mockdata)
	public String mockMessage( HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException, FileUploadException {
		System.out.println(request.getContentType());
		if(mocklock.tryLock()) {
			try {
				MockInfo mockinfo = new MockInfo();
//				System.out.println(mockinfo.getCheckParamsFile().getInputStream().toString());
				DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
				ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
				fileUpload.setHeaderEncoding("UTF-8");
				List<FileItem> list = fileUpload.parseRequest(request);
				for (FileItem item : list) {
					//如果fileitem中封装的是普通输入项的数据
	                if(item.isFormField()){
	                    String name = item.getFieldName();
	                    //解决普通输入项的数据的中文乱码问题
	                    String value = item.getString("UTF-8");
	                    if(StringUtils.isNotEmpty(value)) {
		                    switch (name) {
							case "mockserverip":
								mockinfo.setMockserverip(value);
								break;
							case "mockType":
								mockinfo.setMockType(value);
								break;
							case "ContentType":
								mockinfo.setContentType(value);
								break;
							case "checkUrl":
								mockinfo.setCheckUrl(value);
								break;
							case "checkParams":
								mockinfo.setCheckParams(value);
								break;
							case "responseBody":
								mockinfo.setResponseBody(value);
								break;
							default:
								break;
							}
	                    }
	                }else{
	                    if(StringUtils.isEmpty(mockinfo.getResponseBody())) {
	                    	mockinfo.setResponseBody(item.getString("utf-8"));
	                    }
	                }
				}


				String mockRule = mockMessServiceImpl.mockProcess(mockinfo);
				map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));
			} finally {
				mocklock.unlock();
			}

		}else {
			map.addAttribute("resultmsg", "有人正在mock数据，请稍等或者与最近操作的相关人员"+operaLogServiceImpl.getLastOperByType(CommonConstants.opertype.mockdata.getValue()).getUsername()+"联系");
		}
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
	public String deleteUserInfo(String deleteType, String param, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {

		// 切换数据库
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
		// 切换数据库
		DataSourceContextHolder.setDbType(DataSourceType.SOURCE_ADMIN);
		return "display";
	}

	/**
	 * 修改时间
	 *
	 * @param changetimetype
	 * @param date
	 * @param time
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping({ "changetime" })
	@OperaLogComment(remark = opertype.changtime)
	public String changetime(@RequestParam("changetimetype") String changetimetype, @RequestParam("date") String date,
			@RequestParam("time") String time, HttpServletRequest request, ModelMap map, HttpServletResponse response) {

//		boolean bool = false;

//		Condition condition = lock.newCondition();


		if (changetimelock.tryLock()) {
			try {
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
					cmd = "date -s '" + GetNetworkTimeUtil.getWebsiteDatetime() + "'";
					changeTimeImpl.changeServerTime(ServerInfo.changDubboRestDbtimeIps, cmd);
					break;
				}
				default:
					break;
				}
				map.addAttribute("servernowtime", cmd);
			} finally {
				changetimelock.unlock();
			}
		} else {
			map.addAttribute("resultmsg", "有人正在修改时间，请稍等三分钟");
		}


		return "display";

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
