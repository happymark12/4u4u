package _4u4u.repository.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;
import _4u4u.repository.RoomDao;

// 本類別負責讀取資料庫內Room表格內的紀錄
// 
@Repository
public class RoomDaoImpl implements Serializable, RoomDao {
	private static final long serialVersionUID = 1L;

	
	
	SessionFactory factory ;
	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}


	public RoomDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();
	}
	
	
	public int saveRoom(RoomBean rb) {
		Session session = factory.getCurrentSession();
		Integer n =0;
		session.save(rb);
		n++;
		return n;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RoomBean> getRoomsByFk(RoomRentBean roomAd) {
		List<RoomBean> list = null;
		Session session = factory.getCurrentSession();
		String hql = "FROM RoomBean WHERE roomAd = :roomAd";
		list = session.createQuery(hql)
				.setParameter("roomAd", roomAd)
				.getResultList();
		return list;
	}


	@Override
	public int updateRoom(RoomBean bean) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(bean);
		n++;
		return n;
	}



	
}