import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import _4u4u.model.InterestedAdForRoomAdBean;
import _4u4u.model.MemberBean;
import _4u4u_init.util.HibernateUtils;

public class MyJUnitTest {

	SessionFactory factory = null;
	Session session = null;
	Transaction tx = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("開始測試前的準備工作");
//		factory = HibernateUtils.getSessionFactory();
//		session = factory.getCurrentSession();
//		tx = session.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
//		System.out.println("結束測試後的收尾工作");
		System.out.println("------------3");
//		tx.commit();
		System.out.println("------------4");
//		session.close();
//		factory.close();
	}

	@Test
	public void test() {
	try {
		String test ="test";
		test.substring(3, 12);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
		
