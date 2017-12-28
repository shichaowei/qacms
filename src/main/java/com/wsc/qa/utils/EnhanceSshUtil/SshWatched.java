package com.wsc.qa.utils.EnhanceSshUtil;

import java.io.IOException;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * 被观察者 建立连接但不停止连接
 *
 * @author hzweisc
 *
 */
public class SshWatched extends Observable {
	private static final Logger logger = LoggerFactory.getLogger(SshWatched.class);
	public Session sess = null;
	public Connection conn = null;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}



	public Session getSess() {
		return sess;
	}

	public void setSess(Session sess) {
		this.sess = sess;
	}

	public  SshWatched(String hostname, String username, String password, String cmd) throws IOException {
		conn = new Connection(hostname);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(username, password);
		if (isAuthenticated == false) {
			throw new IOException("Authentication failed.");
		}
		sess = conn.openSession();
		sess.execCommand(cmd);
//		InputStream stdout = sess.getStdout();
//		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//		StringBuilder sb = new StringBuilder();
//		int linenum = 0;
//		while (true) {
//			String line = br.readLine();
//			if (line == null || linenum > 1000) {
//				break;
//			}
//			sb.append(line);
//			sb.append('\n');
//			linenum++;
//		}
		logger.info(hostname + "---ssh执行完的命令为：" + cmd);

	}

	public void closesession() {
		notifyObservers();
	}

}
