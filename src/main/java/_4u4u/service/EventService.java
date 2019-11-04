package _4u4u.service;

import java.util.List;

import _4u4u.model.EventBean;

public interface EventService {
	List<EventBean> getEvents();	//查詢所有活動
	
	EventBean getEventById(int id); //查詢單筆活動
}
