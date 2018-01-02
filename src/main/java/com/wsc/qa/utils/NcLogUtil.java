package com.wsc.qa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsc.qa.constants.ServerInfo;

public abstract class NcLogUtil {
	private static final Logger logger = LoggerFactory.getLogger(NcLogUtil.class);

	private Object[] params;// 方法参数
	/**
	 * @param params
	 *            方法参数
	 */
	public NcLogUtil(Object... params) {
		this.params = params;
		try {
			//server开启funds loan finance dock 日志监听端口
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFundCmd);
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerLoanCmd);
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerFinanceCmd);
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncServerDockCmd);
			//funds日志开始传送
//			SshUtil.remoteRunCmd(ServerInfo.NCfundClientIp, ServerInfo.sshname, ServerInfo.sshpwd,ServerInfo.ncClientFundCmd);
			startLogTransfer(ServerInfo.NCfundClientIp, ServerInfo.ncfdfundLogDir,ServerInfo.ncClientFundCmdTemplate);
			startLogTransfer(ServerInfo.NCloanClientIp, ServerInfo.ncfdloanLogDir,ServerInfo.ncClientLoanCmdTemplate);
			startLogTransfer(ServerInfo.NCfinanceClientIp, ServerInfo.ncfdfinanceLogDir,ServerInfo.ncClientFinanceCmdTemplate);
			startLogTransfer(ServerInfo.NCdockClientIp, ServerInfo.ncfddockLogDir,ServerInfo.ncClientDockCmdTemplate);






			process();
			//停掉server所有的nc监听端口
			SshUtil.remoteRunCmd(ServerInfo.NCServerIp, ServerInfo.sshname, ServerInfo.sshpwd, ServerInfo.ncStopServerCmd);

			System.out.println("over");

		} catch (Exception ex) {
			throw ex;
		}
	}


	/**
	 * 判断日志到底写入的是哪一个文件
	 * 并且开启日志传送
	 *
	 */
	public static void startLogTransfer(String ncclientip,String ncclientlogdir,String ncclientCmdTemplate) {
		String varStr = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
				"cd "+ncclientlogdir+";" + ServerInfo.getFileNames);
		for (String var : varStr.split("\n")) {
			String filetime = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
					"cd "+ncclientlogdir+";date +%s -r " + var);
			String nowtime = SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,
					"date +%s");
			if (Long.valueOf(nowtime) - Long.valueOf(filetime) >= 0
					&& Long.valueOf(nowtime) - Long.valueOf(filetime) <= 60 * 2) {
				System.out.println(ncclientlogdir+":当前日志进入的是" + var);
				//loan日志开始传送
				System.out.println("cmd is "+String.format(ncclientCmdTemplate, var));
				SshUtil.remoteRunCmd(ncclientip, ServerInfo.sshname, ServerInfo.sshpwd,String.format(ncclientCmdTemplate, var));
				break;
			}
		}
	}



	/**
	 *
	 */
	public abstract void process();


}
