package _4u4u_init.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public class SendMatchMailUtil implements Runnable {
	private RoomRentBean roomRentBean;
	private WantedRoomBean wantedRoomBean;
	private List<RoomRentBean> roomRentBeanList;
	private List<WantedRoomBean> wantedRoomBeanList;
	private int option;

	public SendMatchMailUtil() {
	}
	public SendMatchMailUtil(RoomRentBean roomRentBean, List<WantedRoomBean> wantedRoomBeanList) {
		this.roomRentBean = roomRentBean;
		this.wantedRoomBeanList = wantedRoomBeanList;
		this.option = 1;
	}
	public SendMatchMailUtil(WantedRoomBean wantedRoomBean, List<RoomRentBean> roomRentBeanList) {
		this.wantedRoomBean = wantedRoomBean;
		this.roomRentBeanList = roomRentBeanList;
		this.option = 2;
	}
	public SendMatchMailUtil(List<RoomRentBean> roomRentBeanList,WantedRoomBean wantedRoomBean) {
		this.wantedRoomBean = wantedRoomBean;
		this.roomRentBeanList = roomRentBeanList;
		this.option = 3;
	}
	public SendMatchMailUtil(List<WantedRoomBean> wantedRoomBeanList, RoomRentBean roomRentBean) {
		this.roomRentBean = roomRentBean;
		this.wantedRoomBeanList = wantedRoomBeanList;
		this.option = 4;
	}
	
	public void run() {
		String from ="4u4uFindHome@gmail.com";
		String password="Do!ng123";
		String host="smtp.gmail.com";
		int port=465;
		String content = "";
		StringBuffer sbf = new StringBuffer();
		String str = "";
		String[] imageFileNames = null;
		String imageFile = "";
		int n = 0, m = 0;
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
			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");
			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			
			switch (option) {
				case 1:
					// 2.2設定接收人
					String roomRentBeanToAddress = roomRentBean.getRoomRentMemId().getEmail();
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(roomRentBeanToAddress));
					// 2.3設定郵件標題
					message.setSubject("4u4u會員－廣告符合信");
					// 2.4設定郵件内容
					for(WantedRoomBean wantedRoomBean : wantedRoomBeanList) {
						str = "<a href='http://192.168.43.252:8080/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=" + wantedRoomBean.getFindRoomId() + "'><img src='cid:wantedRoomBeanimage" + m + "' width='150' height='150'/></a>" +
							  "<a href='http://192.168.43.252:8080/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=" + wantedRoomBean.getFindRoomId() + "'>" + wantedRoomBean.getAdTitle() + "</a>\n";
						sbf.append(str + "<br>");
						m++;
					}
					content = "<!DOCTYPE html>\n" + 
							"<html lang=\"en\">\n" + 
							"<head>\n" + 
							"    <meta charset=\"UTF-8\">\n" + 
							" \n" + 
							"    <title>4u4u會員－廣告符合信</title>\n" + 
							"</head>\n" + 
							"<body>\n" + 
							"   <h1>此為4u4u會員廣告符合信，請點擊下方連結</h1>\n" + 
							"   <h3>\n" + 
							"找到以下符合的廣告<br>" + sbf +
							"    </h3>\n" + 
							"</body>\n" + 
							"</html>";
					messageBodyPart.setContent(content, "text/html;charset=UTF-8");	
					multipart.addBodyPart(messageBodyPart);
					
					for(WantedRoomBean wantedRoomBean : wantedRoomBeanList) {
						imageFileNames = null;
						if (wantedRoomBean.getImages() != null && wantedRoomBean.getImages().trim().length() != 0) {
							imageFileNames = wantedRoomBean.getImages().trim().split(",");
							imageFile = "d:/images/wantedRoomAd/" + imageFileNames[0];
							messageBodyPart = new MimeBodyPart();
							DataSource wantedRoomBeanimage = new FileDataSource(imageFile);
							messageBodyPart.setDataHandler(new DataHandler(wantedRoomBeanimage));
							str = "<wantedRoomBeanimage" + n + ">";
							messageBodyPart.setHeader("Content-ID", str);
							multipart.addBodyPart(messageBodyPart);
						}
						
						n++;
					}
					break;
				case 2:
					// 2.2設定接收人
					String wantedRoomBeanToAddress = wantedRoomBean.getWantedRoomAdMemId().getEmail();
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(wantedRoomBeanToAddress));
					// 2.3設定郵件標題
					message.setSubject("4u4u會員－廣告符合信");
					// 2.4設定郵件内容
					for(RoomRentBean roomRentBean : roomRentBeanList) {
						str = "<a href='http://192.168.43.252:8080/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=" + roomRentBean.getAdId() + "'><img src='cid:roomRentBeanimage" + m + "' width='150' height='150'/></a>" +
							  "<a href='http://192.168.43.252:8080/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=" + roomRentBean.getAdId() + "'>" + roomRentBean.getAdTitle() + "</a>\n";
						sbf.append(str + "<br>");
						m++;
					}
					content = "<!DOCTYPE html>\n" + 
							"<html lang=\"en\">\n" + 
							"<head>\n" + 
							"    <meta charset=\"UTF-8\">\n" + 
							" \n" + 
							"    <title>4u4u會員－廣告符合信</title>\n" + 
							"</head>\n" + 
							"<body>\n" + 
							"   <h1>此為4u4u會員廣告符合信，請點擊下方連結</h1>\n" + 
							"   <h3>\n" + 
							"找到以下符合的廣告<br>" + sbf +
							"    </h3>\n" + 
							"</body>\n" + 
							"</html>";
					messageBodyPart.setContent(content, "text/html;charset=UTF-8");	
					multipart.addBodyPart(messageBodyPart);
					
					for(RoomRentBean roomRentBean : roomRentBeanList) {
						imageFileNames = null;
						if (roomRentBean.getAdImages() != null && roomRentBean.getAdImages().trim().length() != 0) {
							imageFileNames = roomRentBean.getAdImages().trim().split(",");
							
							imageFile = "d:/images/roomRentAd/" + imageFileNames[0];
							messageBodyPart = new MimeBodyPart();
							DataSource roomRentBeanimage = new FileDataSource(imageFile);
							messageBodyPart.setDataHandler(new DataHandler(roomRentBeanimage));
							str = "<roomRentBeanimage" + n + ">";
							messageBodyPart.setHeader("Content-ID", str);
							multipart.addBodyPart(messageBodyPart);
						}
						
						n++;
					}
					break;
				case 3:
					// 2.2設定接收人
					Set<String> roomRentBeanAddressSet = new LinkedHashSet<String>();
					for(RoomRentBean roomRentBean : roomRentBeanList) {
						roomRentBeanAddressSet.add(roomRentBean.getRoomRentMemId().getEmail()) ;
					}
					
					String roomRentBeanSetToAddress = roomRentBeanAddressSet.toString().replace("[", "").replace("]", "");
					
					@SuppressWarnings("static-access")
					InternetAddress[] roomRentBeanSetInternetAddressTo = new InternetAddress().parse(roomRentBeanSetToAddress);
					message.setRecipients(Message.RecipientType.TO, roomRentBeanSetInternetAddressTo);
					// 2.3設定郵件標題
					message.setSubject("4u4u會員－廣告符合信");
					// 2.4設定郵件内容
					content = "<!DOCTYPE html>\n" + 
							"<html lang=\"en\">\n" + 
							"<head>\n" + 
							"    <meta charset=\"UTF-8\">\n" + 
							" \n" + 
							"    <title>4u4u會員－廣告符合信</title>\n" + 
							"</head>\n" + 
							"<body>\n" + 
							"   <h1>此為4u4u會員廣告符合信，請點擊下方連結</h1>\n" + 
							"   <h3>\n" + 
							"                       找到以下符合的廣告<br>"+ 
							"<a href='http://192.168.43.252:8080/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=" + wantedRoomBean.getFindRoomId() + "'><img src='cid:wantedRoomBeanimage' width='150' height='150'/></a>"   +
							"       <a href='http://192.168.43.252:8080/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=" + wantedRoomBean.getFindRoomId() + "'>" + wantedRoomBean.getAdTitle() + "</a>\n" + 
							"    </h3>\n" + 
							"</body>\n" + 
							"</html>";
					messageBodyPart.setContent(content, "text/html;charset=UTF-8");	
					multipart.addBodyPart(messageBodyPart);
					
					imageFileNames = null;
					if (wantedRoomBean.getImages() != null && wantedRoomBean.getImages().trim().length() != 0) {
						imageFileNames = wantedRoomBean.getImages().trim().split(",");
					}
					imageFile = "d:/images/wantedRoomAd/" + imageFileNames[0];
					messageBodyPart = new MimeBodyPart();
					DataSource wantedRoomBeanimage = new FileDataSource(imageFile);
					messageBodyPart.setDataHandler(new DataHandler(wantedRoomBeanimage));
					messageBodyPart.setHeader("Content-ID", "<wantedRoomBeanimage>");
					multipart.addBodyPart(messageBodyPart);
					
					break;
				case 4:
					// 2.2設定接收人
					Set<String> wantedRoomBeanAddressSet = new LinkedHashSet<String>();
					for(WantedRoomBean wantedRoomBean : wantedRoomBeanList) {
						wantedRoomBeanAddressSet.add(wantedRoomBean.getWantedRoomAdMemId().getEmail()) ;
					}
					
					String wantedRoomBeanSetToAddress = wantedRoomBeanAddressSet.toString().replace("[", "").replace("]", "");
					
					@SuppressWarnings("static-access")
					InternetAddress[] wantedRoomBeanSetInternetAddressTo = new InternetAddress().parse(wantedRoomBeanSetToAddress);
					message.setRecipients(Message.RecipientType.TO, wantedRoomBeanSetInternetAddressTo);
					
//					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					// 2.3設定郵件標題
					message.setSubject("4u4u會員－廣告符合信");
					// 2.4設定郵件内容
					content = "<!DOCTYPE html>\n" + 
							"<html lang=\"en\">\n" + 
							"<head>\n" + 
							"    <meta charset=\"UTF-8\">\n" + 
							" \n" + 
							"    <title>4u4u會員－廣告符合信</title>\n" + 
							"</head>\n" + 
							"<body>\n" + 
							"   <h1>此為4u4u會員廣告符合信，請點擊下方連結</h1>\n" + 
							"   <h3>\n" + 
							"                       找到以下符合的廣告<br>"+ 
							"<a href='http://192.168.43.252:8080/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=" + roomRentBean.getAdId() + "'><img src='cid:roomRentBeanimage' width='150' height='150'/></a>"+
							"<a href='http://192.168.43.252:8080/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=" + roomRentBean.getAdId() + "'>" + roomRentBean.getAdTitle() + "</a>\n" + 
							"    </h3>\n" + 
							"</body>\n" + 
							"</html>";
					messageBodyPart.setContent(content, "text/html;charset=UTF-8");	
					multipart.addBodyPart(messageBodyPart);
					
					imageFileNames = null;
					if (roomRentBean.getAdImages() != null && roomRentBean.getAdImages().trim().length() != 0) {
						imageFileNames = roomRentBean.getAdImages().trim().split(",");
					}
					imageFile = "d:/images/roomRentAd/" + imageFileNames[0];
					messageBodyPart = new MimeBodyPart();
					DataSource roomRentBeanimage = new FileDataSource(imageFile);
					messageBodyPart.setDataHandler(new DataHandler(roomRentBeanimage));
					messageBodyPart.setHeader("Content-ID", "<roomRentBeanimage>");
					multipart.addBodyPart(messageBodyPart);
					break;
			
			} // switch結束
			message.setContent(multipart);
			// 3.發送郵件
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, from, password);
			Transport.send(message);
			System.out.println("信發送成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println(str);
	}
}
