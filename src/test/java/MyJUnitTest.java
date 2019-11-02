import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import _4u4u.model.InterestedAdForRoomAdBean;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
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
	try {
	String hql ="FROM RoomRentBean  r  JOIN  RoomBean  j "
			+ " on   j.roomAd.adId = r.adId "
			+ "WHERE r.adState = true  group by r.adId ORDER BY Max(j.rentPrice) DESC";
	List<Object[]> list = null;
	list = session.createQuery(hql).getResultList();	
	for(Object[] objArray : list) {
		System.out.println("start");
		for(Object obj : objArray) {
			if(obj instanceof RoomRentBean) {
				RoomRentBean bean = (RoomRentBean)obj;
				System.out.println(bean.getAdId());
				
			}
		}
		System.out.println("end");
	}	
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
		
