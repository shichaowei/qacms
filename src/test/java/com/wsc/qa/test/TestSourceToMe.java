package com.wsc.qa.test;

import com.wsc.qa.utils.SshUtil;

public class TestSourceToMe {

	public static void processTransfer(Object paths) {
		// TODO Auto-generated method stub
//		String cmd = "cd /root;find /trdata/jobs/蜂贷3.0| grep fengdai|grep workspace|grep -v classes|grep -v .git>test.txt";
//		String var = SshUtil.remoteRunCmd("10.200.130.105", "root", "Jenkinstest@123098", cmd);
//		new Scpclient("10.200.130.105", 22, "root", "Jenkinstest@123098").getFile("/root/test.txt", "D:/");
//		String tempStr = FileUtil.readFileByLines("D:/test.txt");
//		// System.out.println(var);
//		String[] lines = tempStr.split(",");
		System.out.println(paths.getClass());
		if(paths.getClass().isArray()) {
			String[] lines = (String[]) paths;
			for (String var1 : lines) {
				if(!var1.contains("trdata")) {
					var1="/trdata/jobs/蜂贷3.0/jobs/"+var1+"/workspace";
				}
				// System.out.println(var1);
				if (var1.matches(".*workspace$")) {
					if (var1.contains("fengdai-")&&!var1.contains("deploy")) {
						System.out.print("\""+var1+"\",");
						String temp = "scp -r  " + var1 + " sshuser@192.168.16.185:/D:/51/"+var1.replace("/trdata/jobs/蜂贷3.0/jobs/", "").replace("workspace", "");
						SshUtil.remoteRunCmd("10.200.130.105", "root", "Jenkinstest@123098", temp);
					}
				}
			}
		}else if (String.class.isInstance(paths)) {
			String var1 = (String) paths;
			System.out.println(var1);
			if(!var1.contains("trdata")) {
				var1="/trdata/jobs/蜂贷3.0/jobs/"+var1+"/workspace";
			}
			if (var1.matches(".*workspace$")) {
				if (var1.contains("fengdai-")&&!var1.contains("deploy")) {
					System.out.print("\""+var1+"\",");
					String temp = "scp -r  " + var1 + " sshuser@192.168.16.185:/D:/51/"+var1.replace("/trdata/jobs/蜂贷3.0/jobs/", "").replace("workspace", "");
					SshUtil.remoteRunCmd("10.200.130.105", "root", "Jenkinstest@123098", temp);
				}
			}
		}else {
			System.out.println("rucan wrong");
		}




	}

	public static void main(String[] args) {
		String[] allpaths= {
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-dubbo-funds-test/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-product/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-system/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-rest-funds-callback-test/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-finance/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-dock/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-report/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-operation/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-quartz/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-shop/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-rest-funds-test/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-loan/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-core-funds-test/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-user/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-parent/workspace","/trdata/jobs/蜂贷3.0/jobs/fengdai-common/workspace",
				"/trdata/jobs/蜂贷3.0/jobs/fengdai-dubbo-funds-client-test/workspace",};
//		processTransfer("fengdai-common");
		processTransfer(allpaths);
//		for(String var :allpaths) {
//			String template ="<option value=\"%s\">%s</option>";
//			String aString = var.replace("/trdata/jobs/蜂贷3.0/jobs/", "").replaceAll("/workspace", "");
//			System.out.println(String.format(template, aString,aString));
//		}
	}

}
