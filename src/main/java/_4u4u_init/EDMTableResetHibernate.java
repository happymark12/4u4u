package _4u4u_init;

/*  
    程式說明：建立表格與設定初始測試資料。
    表格包括：Book, BookCompany, Member, Orders, OrderItems
 
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _4u4u.model.EventBean;
import _4u4u_init.util.HibernateUtils;
import _4u4u_init.util.SystemUtils2018;

public class EDMTableResetHibernate {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元

	public static void main(String args[]) {

		

//		int count = 0;
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			// 1. 由"data/bookCompany.dat"逐筆讀入BookCompany表格內的初始資料，
			// 然後依序新增到BookCompany表格中
//			try (
//				FileReader fr = new FileReader("data/bookCompany.dat"); 
//				BufferedReader br = new BufferedReader(fr);
//			) {
//				while ((line = br.readLine()) != null) {
//					if (line.startsWith(UTF8_BOM)) {
//						line = line.substring(1);
//					}
//					String[] token = line.split("\\|");
//					String name = token[0];
//					String address = token[1];
//					String url = token[2];
//					CompanyBean cb = new CompanyBean(null, name, address, url);
//					session.save(cb);
//				}
//			} catch (IOException e) {
//				System.err.println("新建BookCompany表格時發生IO例外: " + e.getMessage());
//			}
//			session.flush();
//			System.out.println("BookCompany 資料新增成功");

//			File file = new File(;
			// 2. 由"data/book.dat"逐筆讀入Event表格內的初始資料，然後依序新增
			// 到Book表格中
			String line = "";
			try (
				FileInputStream fis = new FileInputStream("data/event.dat");
				InputStreamReader isr = new InputStreamReader(fis, "UTF8");
				BufferedReader br = new BufferedReader(isr);
			) {
				while ((line = br.readLine()) != null) {
					System.out.println("line=" + line);
					// 去除 UTF8_BOM: \uFEFF
					if (line.startsWith(UTF8_BOM)) {
						line = line.substring(1);
					}
					String[] token = line.split("\\|");
					EventBean event = new EventBean();		

					event.setDate(Date.valueOf(token[0].trim()));

					// 讀取圖片檔
					Blob blob = SystemUtils2018.fileToBlob(token[1].trim());
					event.setImage(blob);
					event.setImageName(SystemUtils2018.extractFileName(token[1].trim()));
					event.setAddress((token[2].trim()));
					event.setDetail(token[3].trim());
					event.setTotalPeopleCountLimit(Integer.parseInt(token[4].trim()));
					event.setNeedRoomPeopleCount(Integer.parseInt(token[5].trim()));
					event.setHaveRoomPelpleCount(Integer.parseInt(token[6].trim()));
					event.setState(Boolean.parseBoolean(token[7].trim()));
					event.setImageName(token[8].trim());
					event.setEventTitle(token[9].trim());
					session.save(event);
					System.out.println("新增一筆Event紀錄成功");
				}
				// 印出資料新增成功的訊息
				session.flush();
				System.out.println("Event資料新增成功");
			}

            tx.commit();
		} catch (Exception e) {
			System.err.println("新建表格時發生例外: " + e.getMessage());
			e.printStackTrace();
			tx.rollback();
		} 
        factory.close();
	}

}