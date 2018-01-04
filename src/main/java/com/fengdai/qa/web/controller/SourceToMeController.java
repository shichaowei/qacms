package com.fengdai.qa.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fengdai.qa.utils.SshUtil;
/**
 *
 *
 *
 * @author hzweisc
 *
 */
@Controller
public class SourceToMeController {
	private static final Logger logger = LoggerFactory.getLogger(SourceToMeController.class);




	@RequestMapping({ "api/sourcetome" })
	public String sourcetome(String ipaddress,String path,HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {
//			String yuancheng="curl -u hzweisc:111111 http://10.200.130.105:8080/job/蜂贷3.0/view/01_编译_打包-spring-test/job/fengdai-common/build?token=fengdai-common";
//			OkHttpUtil.get(URLEncoder.encode(yuancheng,"utf-8"));
//			given().auth().preemptive().basic("hzweisc", "111111").when().
//				get("http://10.200.130.105:8080/job/蜂贷3.0/view/01_编译_打包-spring-test/job/"+path+"/build?token="+path);
			String var1 = path;
			if(!var1.contains("trdata")) {
				var1="/trdata/jobs/蜂贷3.0/jobs/"+var1+"/workspace";
			}
			if (var1.matches(".*workspace$")) {
				if (var1.contains("fengdai-")&&!var1.contains("deploy")) {
//					System.out.print("\""+var1+"\",");
					String temp = "scp -r  " + var1 + " sshuser@"+ipaddress+":/D:/51/"+var1.replace("/trdata/jobs/蜂贷3.0/jobs/", "").replace("workspace", "");
					SshUtil.remoteRunCmd("10.200.130.105", "root", "Jenkinstest@123098", temp);
				}
			}
		map.addAttribute("resultmsg", "传输成功");
		return "display";
	}

	@RequestMapping({ "sourcetome" })
	public String sourcetomePage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

		return "sourcetome";
	}


}
