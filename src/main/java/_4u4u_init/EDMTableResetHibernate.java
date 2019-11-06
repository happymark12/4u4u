package _4u4u_init;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _4u4u.model.EventBean;
import _4u4u.model.MemberBean;
import _4u4u_init.util.HibernateUtils;
import _4u4u_init.util.SystemUtils2018;

public class EDMTableResetHibernate {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元
	private static SessionFactory factory = HibernateUtils.getSessionFactory();
	private static Session session = factory.getCurrentSession();

	public static void main(String[] args) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			insertMemberTable();
			insertRoomRentAd();
			insertRoom();
			insertEvents();
			tx.commit();
		} catch (Exception e) {
			System.err.println("新建表格時發生例外: " + e.getMessage());
			e.printStackTrace();
			tx.rollback();
		}
		factory.close();
	} // main結束

//	Member表格
	@SuppressWarnings("static-access")
	private static void insertMemberTable() {
		String line = "";
		String imageName = "";
		int count = 0;
		Timestamp timestamp = null;
		try (FileInputStream fis = new FileInputStream("data/Member.txt");
				InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr0);) {
			while ((line = br.readLine()) != null) {
				String[] sa = line.split("\\|");
				MemberBean memberBean = new MemberBean();
//				bean.setMemberId(sa[0]);
				memberBean.setEmail(sa[1]);
				memberBean.setPassword(sa[2]);
				memberBean.setGender(sa[3]);
				memberBean.setPic(SystemUtils2018.fileToBlob(sa[4]));
				memberBean.setName(sa[5]);
				imageName = sa[6].substring(sa[6].lastIndexOf("/") + 1);
				memberBean.setPicName(imageName);
				memberBean.setState(sa[7]);
				if (sa[8].equals("null") == false) {
					memberBean.setVipStart(timestamp.valueOf(sa[8]));
				}
				if (sa[9].equals("null") == false) {
					memberBean.setVipEnd(timestamp.valueOf(sa[9]));
				}
				memberBean.setCreatTime(timestamp.valueOf(sa[10]));

				session.save(memberBean);
				count++;
				System.out.println("新增 " + count + " 筆記錄: " + sa[5]);
			}
			System.out.println("Member表格資料新增成功");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} // Member結束

	public static void insertRoomRentAd() {
		String line = "";
		int count = 0;
		try (	
				FileInputStream fis = new FileInputStream("data/roomRentAdOnlyData.sql");
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
			) {
			while ((line = br.readLine()) != null) {
				try {
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/4u4u?useUnicode=yes&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Taipei",
							"root", "Do!ng123");
					PreparedStatement ps = connection.prepareStatement(line);
					ps.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				count++;
				System.out.println(line);
				System.out.println("新增" + count + "筆紀錄");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertRoom() {
		String line = "";
		int count = 0;
		try (	
				FileInputStream fis = new FileInputStream("data/roomOnlyData.sql");
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
			) {
			while ((line = br.readLine()) != null) {
				try {
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/4u4u?useUnicode=yes&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Taipei",
							"root", "Do!ng123");
					PreparedStatement ps = connection.prepareStatement(line);
					ps.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				count++;
				System.out.println(line);
				System.out.println("新增" + count + "筆紀錄");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertEvents() {
		String line = "";
		int count = 0;
		try (
			FileInputStream fis = new FileInputStream("data/event.dat");
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			BufferedReader br = new BufferedReader(isr);
		) {
			while ((line = br.readLine()) != null) {
				System.out.println("line= " + line);
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
				count++;
				System.out.println("新增" + count + "筆");
			}
			System.out.println("Event資料新增成功");
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}	

}
