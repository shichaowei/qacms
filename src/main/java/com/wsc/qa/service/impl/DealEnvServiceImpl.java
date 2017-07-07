package com.wsc.qa.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import com.wsc.qa.service.DealEnvService;
import com.wsc.qa.utils.SendmailUtil;
import static com.wsc.qa.utils.SshUtil.remoteRunCmd;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * Hello world!
 *
 */
@Service
public class DealEnvServiceImpl implements DealEnvService{
	private static Logger logger = Logger.getLogger(DealEnvServiceImpl.class);

//	public static List<String> dubboAllclasses = new ArrayList<>();

	public  void printpath(ZooKeeper zk, String path,List<String> dubboAllclasses) throws InterruptedException, KeeperException {
		List<String> paths = zk.getChildren(path, false);
		for (String p : paths) {
			printpath(zk, path + "/" + p,dubboAllclasses);
			// System.out.println(path + "/" + p);
			dubboAllclasses.add(path + "/" + p);
		}
	}

	public  void delPath(ZooKeeper zk, String path) throws KeeperException, InterruptedException {
		List<String> paths = zk.getChildren(path, false);
		for (String p : paths) {
			delPath(zk, path + "/" + p);
		}
		for (String p : paths) {
			zk.delete(path + "/" + p, -1);
		}
	}

	

	public  void writefile(String data) {
		try {
			File file = new File("dubboprovider.txt");
			if (!file.exists()) {
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(data);
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  void fixenv(String zkAddress) {
		try {
			ZooKeeper zk = new ZooKeeper(zkAddress, 3000, new Watcher() {
				@Override
				public void process(WatchedEvent event) {

				}
			});
			List<String> dubboAllclasses = new ArrayList<>();
			printpath(zk, "/dubbo",dubboAllclasses);
			Set<String> checkproviderClassset = new HashSet<String>();
			Set<String> chongfuProviderInterset = new HashSet<String>();
			String data="";
			for (String temp : dubboAllclasses) {
				if (temp.contains("providers")) {
					data+=temp;
					data+=";";
					String provider = temp.split("/")[2];
					if(temp.split("/").length == 5){
						String varclass = temp.split("/")[4];
						if (checkproviderClassset.contains(provider) && !varclass.contains("com.fengdai.mqnotify.service.BusinessService")) {
							chongfuProviderInterset.add(provider);
						}
						checkproviderClassset.add(provider);
					}
					
				}
			}
			writefile(data);


			String emailbody = "";
			StringBuffer emailcontent = new StringBuffer();
			Set<String> chongfuFengdaiDubboSet = new HashSet<String>();
			Set<String> chongfu79DubboSet = new HashSet<String>();
			System.out.println("重复的provider接口是：");
			for (String temp : chongfuProviderInterset) {
				System.out.println(temp);
				emailbody += temp;
				emailbody += "<br/>";
				String servicename = temp;
				String[] vattemp = servicename.split("\\.");
				if (servicename.contains("com.fengdai")) {
					chongfuFengdaiDubboSet.add("com.fengdai." + servicename.split("\\.")[2]);
				}
			}
			
			
			System.out.println("重复的蜂贷dubbo服务是：");
			emailbody += "<span class=\"test\">测试环境蜂贷dubbo重复服务</span><br />";
			for (String temp : chongfuFengdaiDubboSet) {
				System.out.println(temp);
				emailbody += temp;
				emailbody += "<br/>";
			}
			
			emailbody += "<span class=\"test\">测试环境解决方法</span><br />";
			emailbody += "砸了它<br/>";
			emailbody += "关机重启<br/>";
			emailbody += "发个红包 联系Harrison Wells<br/>";
			emailbody += "最后一个最靠谱了，快点给我发红包！！！<br/>";

			emailcontent
					.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
					.append("<html>").append("<head>")
					.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
					.append("<title>dubbo环境问题</title>").append("<style type=\"text/css\">")
					.append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")
					.append(".body{font-family:\"Microsoft Yahei\";font-size: 10px;color: black;}").append("</style>")
					.append("</head>").append("<body>").append("<span class=\"test\">测试环境dubbo重复provier节点</span><br />")
					.append(emailbody).append("</body>").append("</html>");

			List<String> receviedAccouts = new ArrayList<>();
			receviedAccouts.add("794440052@qq.com");
			// receviedAccouts.add("hzzyl@*.com");
			SendmailUtil.sendmail(emailcontent.toString(), receviedAccouts);
			
			resetDubboService(chongfuFengdaiDubboSet, dubboAllclasses, zk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 重启相关服务
	 */
	public void resetDubboService(Set<String> chongfuFengdaiDubboSet,List<String> dubboAllclasses,ZooKeeper zk) throws Exception{

			for (String temp : chongfuFengdaiDubboSet) {
				System.out.println(temp);
				for (String providertemp : dubboAllclasses) {
					if (providertemp.contains(temp) && providertemp.contains("providers")
							&& providertemp.split("/").length == 4) {
						delPath(zk, providertemp);
					}
				}
				if (temp.contains("quartz")) {
					remoteRunCmd("172.30.248.31", "root", "Tairantest@123098",
							"/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("activity")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-activity-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("channel")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-channel-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("finance")) {
					System.out.println("finance");
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-finance-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("mqnotify")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-mqnotify-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("mqserver")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-mqserver-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("operation")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-operation-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}

				if (temp.contains("riskcontrol")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-riskcontrol-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("shop")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-shop-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("thirdparty")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-thirdparty-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("transaction-distributed")) {
					remoteRunCmd("172.30.249.243", "root", "Tairantest@123098",
							"/usr/local/dubbo-transaction-distributed-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("authority")) {
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-authority-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("activiti")) {
					System.out.println("activiti");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-fdactiviti-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("electricseal")) {
					System.out.println("electricseal");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-electricseal-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("product")) {
					System.out.println("product");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-product-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("report")) {
					System.out.println("report");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-report-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("system")) {
					System.out.println("system");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-system-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("transaction")) {
					System.out.println("transaction");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-transaction-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}
				if (temp.contains("user")) {
					System.out.println("user");
					remoteRunCmd("172.30.248.217", "root", "Tairantest@123098",
							"/usr/local/dubbo-user-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
				}

				Thread.sleep(20000);

			}
			if(!chongfuFengdaiDubboSet.isEmpty()){
				System.out.println("input restart tomcats");
				remoteRunCmd("172.30.250.25", "root", "Tairantest@123098","/usr/local/restartapachetomcat.sh");
				remoteRunCmd("172.30.251.190", "root", "Tairantest@123098","/usr/local/restartapachetomcat.sh");
				remoteRunCmd("172.30.248.218", "root", "Tairantest@123098","/usr/local/restartapachetomcat.sh");
			}
		
	}


}
