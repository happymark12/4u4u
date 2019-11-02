package _4u4u.repository.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.EmailDao;

@Repository
public class EmailDaoImpl implements EmailDao {
	SessionFactory factory ;
	
	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public boolean needSendEmail(WantedRoomBean wantedRoomBean) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = false;
		Date today = Date.valueOf(sdf.format(new Date(System.currentTimeMillis())));
		Date emailDate = Date.valueOf(sdf.format(wantedRoomBean.getEmailDate()));
		if(today.equals(emailDate)) {
			if(wantedRoomBean.getEmailMax() > wantedRoomBean.getSendMail()) {
				flag = true;
			} else {
				wantedRoomBean.setEmailDate(new Date(emailDate.getTime() + 24*60*60*1000));
				wantedRoomBean.setSendMail(0);
			}
		} else if(today.after(emailDate)) {
			wantedRoomBean.setEmailDate(today);
			wantedRoomBean.setSendMail(0);
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean needSendEmail(RoomRentBean roomRentBean) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = false;
		Date today = Date.valueOf(sdf.format(new Date(System.currentTimeMillis())));
		Date emailDate = Date.valueOf(sdf.format(roomRentBean.getEmailDate()));
		if(today.equals(emailDate)) {
			if(roomRentBean.getEmailMax() > roomRentBean.getSendMail()) {
				flag = true;
			} else {
				roomRentBean.setEmailDate(new Date(emailDate.getTime() + 24*60*60*1000));
				roomRentBean.setSendMail(0);
			}
		} else if(today.after(emailDate)) {
			roomRentBean.setEmailDate(today);
			roomRentBean.setSendMail(0);
			flag = true;
		}
		return flag;
	}
	
	@Override
	public void sendMailAddOne(RoomRentBean roomRentBean) {
		Session session = factory.getCurrentSession();
		roomRentBean.setSendMail(roomRentBean.getSendMail() + 1);
		session.saveOrUpdate(roomRentBean);
	}
	
	@Override
	public void sendMailAddOne(WantedRoomBean wantedRoomBean) {
		Session session = factory.getCurrentSession();
		wantedRoomBean.setSendMail(wantedRoomBean.getSendMail() + 1);
		session.saveOrUpdate(wantedRoomBean);
	}
}
