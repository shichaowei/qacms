package com.wsc.qa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class SshUtil {

	private static final Logger logger = LoggerFactory.getLogger(SshUtil.class);

	/**
	 * 遠程ssh執行命令，最多返回1000行
	 * 如果遲遲不回 默認3s結束
	 *
	 */
	public static String remoteRunCmd(String hostname, String username, String password, String cmd) {
		Connection conn = new Connection(hostname);
		Session sess = null;
		InputStream stdout=null;
		String result =null;
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}
			sess = conn.openSession();
			sess.execCommand(cmd);
			stdout = sess.getStdout();
			result = getResultStr(stdout,3);
			logger.info(hostname + "---ssh执行完的命令为：" + cmd);
		} catch (Exception e) {
			logger.error("ssh error {}", e.getStackTrace());
		} finally {
			sess.close();
			conn.close();
		}
		return result;
	}

	private static String getResultStr(InputStream stdout,int timeout) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws IOException {
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
				StringBuilder sb = new StringBuilder();
				int linenum = 0;
				while (true) {
					String line = br.readLine();
					if (line == null || linenum > 1000) {
						break;
					}
					sb.append(line);
					sb.append('\n');
					linenum++;
				}


				return sb.toString().trim();
			}
		});
		executor.execute(future);
		String result = null;
		try {
			result = future.get(timeout * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.error("get sshdata timeout {}",ExceptionUtil.printStackTraceToString(e));
			result=null;
		}




        try {
        	// 類比：向学生传达“问题解答完毕后请举手示意！”
    		executor.shutdown();
    		// 類比：向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
            // (所有的任务都结束的时候，返回TRUE)
			if(!executor.awaitTermination(3000, TimeUnit.MILLISECONDS)){
			    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			e.printStackTrace();
		}
		logger.info("ssh result \n {}", result);
		return result;
	}

	/**
	 * 遠程ssh執行命令，最多返回1000行 mock的shell命令后台运行没有返回值
	 *
	 */
	public static String remoteRunCmdAddDelay(String hostname, String username, String password, String cmd,
			boolean readflag) {

		Connection conn = new Connection(hostname);
		Session sess = null;

		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}
			sess = conn.openSession();
			sess.execCommand(cmd);
			InputStream stdout = sess.getStdout();
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			StringBuilder sb = new StringBuilder();
			int linenum = 0;
			if (readflag) {
				while (true) {
					String line = br.readLine();
					if (line == null || linenum > 1000) {
						break;
					}
					sb.append(line);
					sb.append('\n');
					linenum++;
				}
			} else {
				Thread.sleep(5000);
			}
			return sb.toString();
		} catch (Exception e) {
			return "false";
		} finally {
			sess.close();
			conn.close();
		}
	}

	public static void main(String[] args) {
		// remoteRunCmd("10.200.141.38", "root", "Tairan@2017",
		// "/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart",true);
		// SshUtil.remoteRunCmd(ServerInfo.quartzIpadd, ServerInfo.sshname,
		// ServerInfo.sshpwd,
		// ServerInfo.restartquartzCmd,false);
		System.out.println("sfsdffd".substring(0, 10));
	}

}