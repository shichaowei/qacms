package com.wsc.qa.service.impl;

import org.springframework.stereotype.Service;

import com.wsc.qa.service.ChangeTimeService;
import com.wsc.qa.utils.SshUtil;

@Service
public class ChangeTimeServiceImpl implements ChangeTimeService{
	
	
	public void changeServerTime(String[] ipaddress,String cmd) {
			boolean flag=false;
			do {
				try {
					SshUtil.remoteRunCmd("172.30.248.31", "root", "Tairantest@123098",
							"/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh stop");
					for (int i = 0; i < ipaddress.length; i++) {
						SshUtil.remoteRunCmd(ipaddress[i], "root", "Tairantest@123098", cmd);
					}
					SshUtil.remoteRunCmd("172.30.248.31", "root", "Tairantest@123098",
							"/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
					flag = true;
				} catch (Exception e) {
					flag = false;
				} 
			} while (!flag);
	}

}
