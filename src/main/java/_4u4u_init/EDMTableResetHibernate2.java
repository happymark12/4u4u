package _4u4u_init;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _4u4u.model.MemberBean;
import _4u4u_init.util.HibernateUtils;
import _4u4u_init.util.SystemUtils2018;

public class EDMTableResetHibernate2 {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元
	private static SessionFactory factory = HibernateUtils.getSessionFactory();
	private static Session session = factory.getCurrentSession();
	
	public static void main(String[] args) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			insertMemberTable();
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
	private static void insertMemberTable(){
		String line = "";
		String imageName = "";
		int count = 0;
		Timestamp timestamp = null;
		try (
			FileInputStream fis = new FileInputStream("data/Member.txt");
			InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr0);
		) {
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
				if(sa[8].equals("null") == false) {
					memberBean.setVipStart(timestamp.valueOf(sa[8]));
				}
				if(sa[9].equals("null") == false) {
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
	
	
	
}