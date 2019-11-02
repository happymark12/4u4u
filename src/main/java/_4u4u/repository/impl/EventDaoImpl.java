package _4u4u.repository.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _4u4u.model.EventBean;
import _4u4u.repository.EventDao;
import _4u4u_init.util.HibernateUtils;

@Repository
public class EventDaoImpl implements Serializable, EventDao{		
	private static final long serialVersionUID = 1L;
	
	SessionFactory factory;
	
	@Autowired	//MVC多了注入實例
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	public EventDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();	//建立工廠
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventBean> getEvents() {
		List<EventBean> list = null;
		Session session = factory.getCurrentSession();	//呼叫session物件
		String hql = "FROM EventBean";	//匯入到hql
		list = session.createQuery(hql).getResultList();
		return list;
	}

	@Override
	public EventBean getEventById(int id) {
		Session session = factory.getCurrentSession();
		EventBean bean = null;
		bean = session.get(EventBean.class, id);
		return bean;
	}

}
