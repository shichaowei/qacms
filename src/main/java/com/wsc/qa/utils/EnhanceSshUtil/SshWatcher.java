package com.wsc.qa.utils.EnhanceSshUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsc.qa.utils.LogPrepareUtil;

import ch.ethz.ssh2.Session;

/**
 * 根據被觀察者是否要關掉連接從而關閉連接
 * @author hzweisc
 *
 */
public class SshWatcher implements Observer{
	private static final Logger logger = LoggerFactory.getLogger(SshWatcher.class);

	public SshWatcher(SshWatched sshWatched) {
		sshWatched.addObserver(this);
	}

	public String GetRequestResult(Session sess) {
		InputStream stdout = sess.getStdout();
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		StringBuilder sb = new StringBuilder();
		int linenum = 0;
		while (true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				line = null;
			}
			if (line == null || linenum > 1000) {
				break;
			}
			sb.append(line);
			sb.append('\n');
			linenum++;
		}
		String result =sb.toString();
		logger.info("result is **{}",result);
		return result;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		new LogPrepareUtil() {

			@Override
			public void process() {
				GetRequestResult(((SshWatched)o).getSess());
			}
		};
		((SshWatched)o).getSess().close();
		((SshWatched)o).getConn().close();

	}

}
