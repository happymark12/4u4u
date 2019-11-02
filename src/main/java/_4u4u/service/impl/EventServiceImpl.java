package _4u4u.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.EventBean;
import _4u4u.repository.EventDao;
import _4u4u.service.EventService;
@Transactional
@Service
public class EventServiceImpl implements Serializable, EventService{	
	
	private static final long serialVersionUID = 1L;
		
	EventDao dao = null;
	
	@Autowired	//MVC多了注入實例
	public void setDao(EventDao dao) {
		this.dao = dao;
	}
	
	public EventServiceImpl() {
//		dao = new EventDaoImpl();
	}

	@Override
	public List<EventBean> getEvents() {		
		return dao.getEvents();
	}

	@Override
	public EventBean getEventById(int id) {
		return dao.getEventById(id);
	}

	

}
