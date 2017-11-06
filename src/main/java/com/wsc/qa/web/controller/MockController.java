package com.wsc.qa.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.meta.CallbackInfo;
import com.wsc.qa.meta.GetRequestResult;
import com.wsc.qa.meta.MockInfo;
import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.service.FengdaiCallbakInfoService;
import com.wsc.qa.service.MockMessService;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.utils.FormateDateUtil;
import com.wsc.qa.utils.GetUserUtil;
import com.wsc.qa.utils.OkHttpUtil;
import com.wsc.qa.utils.SmilarJSONFormatUtil;
import com.wsc.qa.utils.SshUtil;

@Controller
public class MockController {
	private static final Logger logger = LoggerFactory.getLogger(MockController.class);




	@Resource
	private ChangeTimeService changeTimeImpl;
	@Autowired
	private OperLogService operaLogServiceImpl;

	@Autowired
	private MockMessService mockMessServiceImpl;

	@Autowired
	private FengdaiCallbakInfoService fengdaiCallbakInfoServiceImpl;
	/**修改时间需要用到锁，同一时间只有一个人在修改，如果在方法中的lock变量是局部变量，每个线程执行该方法时都会保存一个副本，
	那么每个线程执行到lock.lock()处获取的是不同的锁，所以就不会对临界资源形成同步互斥访问。因此，我们只需要将lock声明为成员变量即可
	**/
	Lock changetimelock = new ReentrantLock();
	Lock mocklock = new ReentrantLock();


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
	 * @throws Exception
	 */
	@RequestMapping({ "addmockrule" })
	@OperaLogComment(remark = opertype.mockdata)
	public String addmockrule( HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws Exception {
		if(mocklock.tryLock()) {
			try {
				MockInfo mockinfo = new MockInfo();
//				System.out.println(mockinfo.getCheckParamsFile().getInputStream().toString());
				DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
				ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
//				fileUpload.setHeaderEncoding("UTF-8");
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
	                    	InputStream in = item.getInputStream();
	                    	byte[] b = new byte[3];
	                    	in.read(b);
	                    	in.close();
	                    	if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
	                    		mockinfo.setResponseBody(item.getString("utf-8"));
	                    	}
	                    	else {
	                    		mockinfo.setResponseBody(item.getString("gbk").trim());
	                    	}

	                    }
	                }
				}
				mockinfo.setMocktime(FormateDateUtil.format(Calendar.getInstance().getTime()));
				mockinfo.setOpername(GetUserUtil.getUserName(request));
				mockMessServiceImpl.addMockinfo(mockinfo);
				List<MockInfo> var = mockMessServiceImpl.getAllMockInfos();
				logger.info("mock的数据是:{}",var);
//				String mockRule = mockMessServiceImpl.mockProcess(var);
//				logger.info("mock的rule{}",mockRule);
//				map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));
				map.addAttribute("mockrules",var);

			} finally {
				mocklock.unlock();
			}

		}else {
			map.addAttribute("resultmsg", "有人正在添加mock数据，请稍等或者与最近操作的相关人员"+operaLogServiceImpl.getLastOperByType(CommonConstants.opertype.mockdata.getValue()).getUsername()+"联系");
		}
		return "mockInfoDis";
	}








	@RequestMapping({ "startmock" })
	@OperaLogComment(remark = opertype.startmock)
	public String startmock( HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException, FileUploadException {
		List<MockInfo> var = mockMessServiceImpl.getAllMockInfos();
		String mockRule = mockMessServiceImpl.mockProcess(var);
		logger.info("mock的rule:{}",mockRule);
		map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));
		map.addAttribute("resultmsg","mock成功");
		return "display";
	}

	@RequestMapping({ "resetmock" })
	@OperaLogComment(remark = opertype.startmock)
	public String resetmock( HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException, FileUploadException {
		mockMessServiceImpl.deleteAllMockinfo();
		SshUtil.remoteRunCmd(ServerInfo.mockServerIp, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ServerInfo.restartanyproxyShellMode, CommonConstants.ShellInfo.restanyproxyrule.getValue()), false);
//		map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));
		map.addAttribute("resultmsg","重置mock成功");
		return "display";
	}

	@RequestMapping({ "deletemockrules" })
	@OperaLogComment(remark = opertype.deletemockrules)
	public String deletemockrules( int id,HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException, FileUploadException {
		mockMessServiceImpl.deleteMockinfoByid(id);
		SshUtil.remoteRunCmd(ServerInfo.mockServerIp, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ServerInfo.restartanyproxyShellMode, CommonConstants.ShellInfo.restanyproxyrule.getValue()), false);
//		map.addAttribute("mockRuleStr", SmilarJSONFormatUtil.format(mockRule));
		map.addAttribute("resultmsg","删除mock规则成功");
		return "display";
	}

	@RequestMapping({ "gethttpinterface" })
	@OperaLogComment(remark = opertype.gethttpinterface)
	public String gethttpinterface( GetRequestResult getInterfaceInfo, HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException, FileUploadException {
		String result = null;

		if(getInterfaceInfo.getRequestType().equals("get")) {
			if(StringUtils.isEmpty(getInterfaceInfo.getToken())) {
				result = OkHttpUtil.get(getInterfaceInfo.getRequestUrl());
			}else {
				result = OkHttpUtil.getWithCookie(getInterfaceInfo.getRequestUrl(),getInterfaceInfo.getToken());
			}
		}else if (getInterfaceInfo.getRequestType().equals("post")&&getInterfaceInfo.getRequestContentType().equals("application/json")) {
			if(StringUtils.isEmpty(getInterfaceInfo.getToken())) {
				result = OkHttpUtil.post(getInterfaceInfo.getRequestUrl(), getInterfaceInfo.getRequestBody());
			}else {
				result = OkHttpUtil.postWithCookies(getInterfaceInfo.getRequestUrl(),getInterfaceInfo.getRequestBody(),getInterfaceInfo.getToken());
			}

		}else if (getInterfaceInfo.getRequestType().equals("post")&&getInterfaceInfo.getRequestContentType().equals("application/x-www-form-urlencoded")) {
			Map<String, Object> body = (Map)JSON.parse(getInterfaceInfo.getRequestBody());
			if(StringUtils.isEmpty(getInterfaceInfo.getToken())) {
				result = OkHttpUtil.postForm(getInterfaceInfo.getRequestUrl(), body);
			}else {
				result = OkHttpUtil.postFormWithCookie(getInterfaceInfo.getRequestUrl(),body,getInterfaceInfo.getToken());
			}

		}
		logger.info("http請求的結果是{}",result);
		map.addAttribute("gethttpinterface", result);
		return "display";
	}









}
