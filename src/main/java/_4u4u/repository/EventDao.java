package _4u4u.repository;

import java.util.List;

import _4u4u.model.EventBean;

public interface EventDao {
	List<EventBean> getEvents();	//查詢所有活動
	
	EventBean getEventById(int id); //查詢單筆活動

}
