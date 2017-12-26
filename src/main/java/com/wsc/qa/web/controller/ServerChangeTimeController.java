package com.wsc.qa.web.controller;

import static io.restassured.RestAssured.given;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants;
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.FengdaiDbNewService;
import com.wsc.qa.service.FengdaiDbNewUserInfoService;
import com.wsc.qa.utils.GetNetworkTimeUtil;
import com.wsc.qa.utils.GetUserUtil;

import io.restassured.response.Response;

@Controller
public class ServerChangeTimeController {

	@Autowired
	private FengdaiDbNewUserInfoService fengdaiUserInfoServiceImpl;
	@Autowired
	private FengdaiDbNewService fengdaiServiceImpl;

	@Resource
	private ChangeTimeService changeTimeImpl;

	/**
	 * 修改时间需要用到锁，同一时间只有一个人在修改，如果在方法中的lock变量是局部变量，每个线程执行该方法时都会保存一个副本，
	 * 那么每个线程执行到lock.lock()处获取的是不同的锁，所以就不会对临界资源形成同步互斥访问。因此，我们只需要将lock声明为成员变量即可
	 **/
	Lock changetimelock = new ReentrantLock();
	Lock newchangetimelock = new ReentrantLock();

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
	@RequestMapping({ "/api/changetime" })
	@OperaLogComment(remark = opertype.changtime)
	public String changetime(@RequestParam("changetimetype") String changetimetype, @RequestParam("date") String date,
			@RequestParam("time") String time, HttpServletRequest request, ModelMap map, HttpServletResponse response) {

		// boolean bool = false;

		// Condition condition = lock.newCondition();

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

	/**
	 * 修改3.0时间触发相关任务
	 *
	 * @param changetimetype
	 * @param date
	 * @param time
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping({ "/api/newchangetime" })
	@OperaLogComment(remark = opertype.changtime)
	public String newChangeTime(@RequestParam("changetimetype") String changetimetype,
			@RequestParam("jobid") String jobid, @RequestParam("date") String date, @RequestParam("time") String time,
			HttpServletRequest request, ModelMap map, HttpServletResponse response)
			throws InterruptedException, UnsupportedEncodingException {

		// boolean bool = false;

		// Condition condition = lock.newCondition();

		if (newchangetimelock.tryLock()) {
			try {
				String cmd = "date -s '" + date + " " + time + "'";
				switch (changetimetype) {

				case "changeServicetime": {
					changeTimeImpl.newchangeServerTime(ServerInfo.changeServicetimeIps, cmd);
					break;
				}
				case "changServiceDbtime": {
					changeTimeImpl.newchangeServerTime(ServerInfo.changeServiceDbtimeIps, cmd);
					break;
				}

				case "restAllTime": {
					cmd = "date -s '" + GetNetworkTimeUtil.getWebsiteDatetime() + "'";
					changeTimeImpl.newchangeServerTime(ServerInfo.changeServiceDbtimeIps, cmd);
					break;
				}
				default:
					break;
				}
				Thread.sleep(2000);
				//修改时间必须走信用卡账单更新 要不账单会出错
				if (!CommonConstants.creditupdatejob.equals(jobid)) {
					Response temp = given().param("userName", "fengdai").param("password", "fengdai2017")
							.param("ifRemember", "on").post("http://10.200.141.52:8081/schedule-admin/login");
					given().param("id", Integer.valueOf(1023)).cookies(temp.getCookies())
							.post("http://10.200.141.52:8081/schedule-admin/jobinfo/trigger");

				}

				if (!"*".equals(jobid)) {
					Response temp = given().param("userName", "fengdai").param("password", "fengdai2017")
							.param("ifRemember", "on").post("http://10.200.141.52:8081/schedule-admin/login");
					given().param("id", Integer.valueOf(jobid)).cookies(temp.getCookies())
							.post("http://10.200.141.52:8081/schedule-admin/jobinfo/trigger");
				}
				map.addAttribute("servernowtime", cmd);

				String dingdingbody = "{\"msgtype\":\"text\",\"text\":{\"content\":\"%s---修改时间:%s\"}}";
				given().header("Content-Type", "application/json")
						.body(String.format(dingdingbody, GetUserUtil.getUserName(request), cmd))
						.post(CommonConstants.DINGDINGJQR);
			} finally {
				newchangetimelock.unlock();
			}
		} else {
			map.addAttribute("resultmsg", "有人正在修改时间，请稍等三分钟");
		}

		return "display";

	}

}
