package com.wsc.qa.utils;


import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SendmailUtil {

	public static void sendmail(String content,List<String> receiveMailAccounts) throws Exception {
		String myEmailAccount = "hzweisc@tairanchina.com";
		String myEmailPassword = "qwer@1234";

		// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
		String myEmailSMTPHost = "smtp.mxhichina.com";

		// 收件人邮箱（替换为自己知道的有效邮箱）
//		String receiveMailAccount = "1239378293@qq.com";
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP
																// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getDefaultInstance(props);
		// session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccounts,content);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		transport.connect(myEmailAccount, myEmailPassword);

		transport.sendMessage(message, message.getAllRecipients());

		transport.close();
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMimeMessage(Session session, String sendMail, List<String> receiveMailAccounts,String content) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, "dubbo环境告警", "UTF-8"));

		List<InternetAddress> list = new ArrayList<InternetAddress>();
		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		for(String receiveMail:receiveMailAccounts){
			list.add(new InternetAddress(receiveMail));
		}
		InternetAddress[] receiveAddres =(InternetAddress[])list.toArray(new InternetAddress[list.size()]);
		message.setRecipients(MimeMessage.RecipientType.TO, receiveAddres);
		
		// 4. Subject: 邮件主题
		message.setSubject("测试环境告警", "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）
		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}

}