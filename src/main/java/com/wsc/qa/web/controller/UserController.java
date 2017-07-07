package com.wsc.qa.web.controller;

import java.io.IOException;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wsc.qa.meta.User;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.UserService;
import com.wsc.qa.service.impl.ChangeTimeServiceImpl;
import com.wsc.qa.service.impl.DealEnvServiceImpl;
import com.wsc.qa.service.impl.OperLogServiceImpl;
import com.wsc.qa.service.impl.UserServiceImpl;
import com.wsc.qa.utils.LogUtil;
import com.wsc.qa.annotation.Log;



@Controller
public class UserController {
	final  LogUtil logger  =  new LogUtil(this.getClass());
	
	@Autowired
	private UserService userServiceImpl;
	
	@Resource
	private ChangeTimeService changeTimeImpl;
	@Autowired
	private OperLogService operaLogServiceImpl;
	@Autowired
	private DealEnvService dealEnvServiceImpl;
	
	@RequestMapping({"login"})
	@Log(operationType="login操作:",operationName="登录")  
	public void login(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword
			,HttpServletResponse response,HttpServletRequest request) throws IOException{
		logger.logInfo("username is:"+userName);
		User user=userServiceImpl.getUserInfo(userName);
		if(user != null){
				if(user.getUserPassword().equals(userPassword)){
					HttpSession session = request.getSession();
					Cookie userNameCookie = new Cookie("userName", userName);
					Cookie pwdCookie = new Cookie("pwd", userPassword);
					userNameCookie.setMaxAge(10 * 60);
					pwdCookie.setMaxAge(10 * 60);
					
					session.setAttribute("userName", userName);
					response.addCookie(userNameCookie);
					response.addCookie(pwdCookie);
					
					
					response.sendRedirect("user/"+userName);
				}
				else{
					response.sendRedirect("user/error");
				}
			
		}else{
			response.sendRedirect("user/error");
		}
	}
	
	
	@RequestMapping({"user/{userName}"})
	@Log(operationType="getInfo操作:",operationName="获取用户信息")  
	public String getInfo(@PathVariable("userName") String userNameRe,HttpServletRequest request,ModelMap map,
			HttpServletResponse response){
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		if(userName !=null && userName.equals(userNameRe)){
			map.addAttribute("userName", userNameRe);
			map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
			map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
			map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
			return "user";
		}else{
			return "error";
		}
	}
	
	@RequestMapping({"fixenv"})
	public String fixenv(@RequestParam("zkAddress") String zkAddress,HttpServletRequest request,ModelMap map,HttpServletResponse response){
		dealEnvServiceImpl.fixenv(zkAddress);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		map.addAttribute("userName", userName);
		map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
		map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
		map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
		//插入最後操作的數據
		operaLogServiceImpl.insertOperLog(userName, "fixenv");
		return "user";
	}
	
	@RequestMapping({"changedubbotime"})
	public String changetime(@RequestParam("changetimetype") String changetimetype,@RequestParam("date") String date,
		@RequestParam("time") String time,HttpServletRequest request,ModelMap map,HttpServletResponse response){
		String cmd="date -s '"+date+" "+time+"'";
		switch (changetimetype) {
		
			case "changeDubbotime":{
				String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243"};
				
				changeTimeImpl.changeServerTime(ipaddress, cmd);
				break;
			}
			case "changDubboDbtime":{
				String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243","172.30.249.242"};
				changeTimeImpl.changeServerTime(ipaddress, cmd);
				break;
			}
			case "changDubboResttime":{
				String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243","172.30.250.25","172.30.248.218","172.30.251.190"};
				changeTimeImpl.changeServerTime(ipaddress, cmd);
				break;
			}
			case "changDubboRestDbtime":{
				String ipaddress[] = { "172.30.248.31", "172.30.248.217", "172.30.249.243","172.30.250.25","172.30.248.218","172.30.251.190","172.30.249.242"};
				changeTimeImpl.changeServerTime(ipaddress, cmd);
				break;
			}
			default:
				break;
		}
		//插入最後操作的數據
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		operaLogServiceImpl.insertOperLog(userName, changetimetype);

		map.addAttribute("userName", userName);
		map.addAttribute("lastoperaName",operaLogServiceImpl.getLastOper().getUsername());
		map.addAttribute("lastoperaType",operaLogServiceImpl.getLastOper().getOpertype());
		map.addAttribute("lastoperaTime",operaLogServiceImpl.getLastOper().getOpertime());
		return "user";

	}
	
	
	
	
	
	

}
