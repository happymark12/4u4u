import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import _4u4u.model.MemberBean;
import _4u4u.model.MessagesBean;
import _4u4u_init.util.HibernateUtils;

public class MyJUnitTest {

	SessionFactory factory = null;
	Session session = null;
	Transaction tx = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("開始測試前的準備工作");
		factory = HibernateUtils.getSessionFactory();
		session = factory.getCurrentSession();
		tx = session.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
//		System.out.println("結束測試後的收尾工作");
		System.out.println("------------3");
		tx.commit();
		System.out.println("------------4");
//		session.close();
		factory.close();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		MemberBean tMb =session.get(MemberBean.class, 8);
		MemberBean fMb =session.get(MemberBean.class, 17);
		String hql2 = "FROM MessagesBean m WHERE (m.targetMemId= :tmb and "
				+ " m.sendMemId=:smb) OR (m.targetMemId= :smb and " 
				+"	 m.sendMemId=:tmb) order by sendTime";
		List<MessagesBean> list = session.createQuery(hql2)
				.setParameter("tmb", tMb)
			    .setParameter("smb", fMb)
			    .getResultList();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a | MM-dd");
		
		for(MessagesBean bean : list) {
			System.out.println(sdf.format(new Date(bean.getSendTime().getTime())));
			
			
		}
		
		
		
	}

		
}