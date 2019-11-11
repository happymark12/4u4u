package _4u4u_init.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailUtil implements Runnable {
	private String email;	//會員信箱
	private String code;	//開通碼

	public MailUtil() {
		super();
	}
	public MailUtil(String email, String code) {
		this.email = email;
		this.code = code;
	}
	
	public void run() {
		String from ="4u4uFindHome@gmail.com";
		String password="Do!ng123";
		String host="smtp.gmail.com";
		int port=465;
		// 1.創建連接對象javax.mail.Session
		// 2.創建郵件對象 javax.mail.Message
		// 3.發送一封開通信
//		Properties properties = System.getProperties();		// 取得系统屬性
		Properties properties = new Properties();
		properties.put("mail.smtp.host",host);	// 設定郵件伺服器
		properties.put("mail.smtp.port",port);
		properties.put("mail.smtp.auth", "true");	// 打開認證

		try {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);
			
			
			// 1.取得預設session
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password); // 寄件方帳號密碼
				}
			});
			// 2.創建郵件
			Message message = new MimeMessage(session);
			// 2.1設定發信人
			message.setFrom(new InternetAddress(from));
			// 2.2設定接收人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			// 2.3設定郵件標題
			message.setSubject("4u4u會員註冊－帳號開通信");
			// 2.4設定郵件内容
			String content = "<!DOCTYPE html>\n" + 
					"<html lang=\"en\">\n" + 
					"<head>\n" + 
					"    <meta charset=\"UTF-8\">\n" + 
					" \n" + 
					"    <title>4u4u會員帳號開通信</title>\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"   <h1>此為4u4u會員帳號開通信，開通請點擊下方連結</h1>\n" + 
					"   <h3>\n" + 
					"       <a href='http://192.168.43.252:8080/4u4u/ProcessMemberState.do?code="+ 
					 code + "&email="+email+"'>激活帳戶</a>\n" + 
					"    </h3>\n" + 
					"    <p>如果未註冊帳號，請直接忽略此信</p>\n" + 
					"</body>\n" + 
					"</html>";
			message.setText(content);
			message.setContent(content, "text/html;charset=UTF-8");
			// 3.發送郵件
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, from, password);
			Transport.send(message);
			System.out.println("開通信發送成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
