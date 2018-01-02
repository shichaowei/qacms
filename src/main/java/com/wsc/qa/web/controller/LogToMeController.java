package com.wsc.qa.web.controller;

import static com.wsc.qa.utils.NcLogUtil.startLogTransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wsc.qa.annotation.OperaLogComment;
import com.wsc.qa.constants.CommonConstants.opertype;
import com.wsc.qa.constants.ServerInfo;
import com.wsc.qa.service.OperLogService;
import com.wsc.qa.service.RedisService;
import com.wsc.qa.utils.SshUtil;

@Controller
public class LogToMeController {

	private static final Logger logger = LoggerFactory.getLogger(SourceToMeController.class);

	/**
	 * 修改时间需要用到锁，同一时间只有一个人在修改，如果在方法中的lock变量是局部变量，每个线程执行该方法时都会保存一个副本，
	 * 那么每个线程执行到lock.lock()处获取的是不同的锁，所以就不会对临界资源形成同步互斥访问。因此，我们只需要将lock声明为成员变量即可
	 **/
	Lock logtomelock = new ReentrantLock();

	@Autowired
	private RedisService redisServiceImpl;

	@Autowired
	private OperLogService operLogServiceImpl;

	@RequestMapping({ "/api/logtome" })
	@OperaLogComment(remark = opertype.logtome)
	public String sourcetome(String ipaddress, String type, String time, HttpServletRequest request,
			HttpServletResponse response, ModelMap map) throws IOException {
		if ("start".equals(type)) {
			// 如果被占用了超过租用的时间，解除锁，关闭监听状态；如果还在租用时间内，开启提示模式
			if (!redisServiceImpl.exists("logtome")) {
				// 停掉server所有的nc监听端口 解除锁
				SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
						ServerInfo.ncStopServerCmd);
				// 废弃不用，一个线程不能unlock另一个线程的锁
				// logtomelock.unlock();
				SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
						"scp -r /root/serverlog admin@" + ipaddress + ":/D:/abc/");
				logger.info("时间到了，结束监听，日志传送到{}", ipaddress + ":/D:/abc/");
			}
			if (logtomelock.tryLock()) {
				try {
					// server开启funds loan finance dock 日志监听端口
					SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
							ServerInfo.ncServerFundCmd);
					SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
							ServerInfo.ncServerLoanCmd);
					SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
							ServerInfo.ncServerFinanceCmd);
					SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
							ServerInfo.ncServerDockCmd);
					// funds日志开始传送
					// SshUtil.remoteRunCmd(ServerInfo.NCfundClientIp, ServerInfo.sshname,
					// ServerInfo.sshpwd,ServerInfo.ncClientFundCmd);
					startLogTransfer(ServerInfo.NCfundClientIp, ServerInfo.ncfdfundLogDir,
							ServerInfo.ncClientFundCmdTemplate);
					startLogTransfer(ServerInfo.NCloanClientIp, ServerInfo.ncfdloanLogDir,
							ServerInfo.ncClientLoanCmdTemplate);
					startLogTransfer(ServerInfo.NCfinanceClientIp, ServerInfo.ncfdfinanceLogDir,
							ServerInfo.ncClientFinanceCmdTemplate);
					startLogTransfer(ServerInfo.NCdockClientIp, ServerInfo.ncfddockLogDir,
							ServerInfo.ncClientDockCmdTemplate);
					redisServiceImpl.set("logtome", "listening", Long.valueOf(time) * 60L);
					map.addAttribute("resultmsg", "开始监听,请记住回来关闭监听哦,否则");
				} finally {
					// 主要解决的是并发请求 懵逼状态
					logtomelock.unlock();
				}
			} else {
				map.addAttribute("resultmsg", "有人正在监听日志，请联系相关测试人员是否要停掉,可能是:"
						+ operLogServiceImpl.getLastOperByType(opertype.logtome.getValue()).getUsername());
			}

		} else {
			// 停掉server所有的nc监听端口 解除锁
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
					ServerInfo.ncStopServerCmd);
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd,
					"scp -r /root/serverlog admin@" + ipaddress + ":/D:/abc/");
			map.addAttribute("resultmsg", "主动结束监听");

			redisServiceImpl.remove("logtome");
		}

		return "logtome";
	}

	@RequestMapping(value = "/api/downloadlog", method = RequestMethod.GET)
	public void testDownload(HttpServletRequest request, HttpServletResponse res, ModelMap map) {
		String fileName = "logs.zip";
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = res.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(new File(ServerInfo.ncServerLogDir + fileName)));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("success");
	}

	@RequestMapping({ "logtome" })
	public String sourcetomePage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

		return "logtome";
	}
}
