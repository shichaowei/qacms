package com.wsc.qa.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
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
import com.wsc.qa.utils.GetNetworkTimeUtil;
import com.wsc.qa.utils.GetUserUtil;
import com.wsc.qa.utils.JsonFormatUtil;
import com.wsc.qa.utils.OkHttpUtil;
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
	public String deleteUserInfo(String deleteType, String param, String moneynumStr,HttpServletRequest request, ModelMap map,
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
