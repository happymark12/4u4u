package _4u4u.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.EmailDao;
import _4u4u.repository.RoomRentDao;
import _4u4u.repository.WantedRoomDao;
import _4u4u.service.EmailService;
import _4u4u_init.util.SendMatchMailUtil;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	EmailDao emaildao;
	RoomRentDao roomRentDao;
	WantedRoomDao wantedRoomDao;
	
	@Autowired
	public void setEmaildao(EmailDao emaildao) {
		this.emaildao = emaildao;
	}
	@Autowired
	public void setRoomRentDao(RoomRentDao roomRentDao) {
		this.roomRentDao = roomRentDao;
	}
	@Autowired
	public void setWantedRoomDao(WantedRoomDao wantedRoomDao) {
		this.wantedRoomDao = wantedRoomDao;
	}

	@Override
	public void sendMatchEmail(RoomRentBean roomRentBean) {
		List<WantedRoomBean> wantedRoomBeanList = roomRentDao.findingMatchWantedRoomAd(roomRentBean);
		if(emaildao.needSendEmail(roomRentBean) && wantedRoomBeanList.size() > 0) {
			new Thread(new SendMatchMailUtil(roomRentBean, wantedRoomBeanList)).start();
			emaildao.sendMailAddOne(roomRentBean);
		}
		
		Iterator<WantedRoomBean> it = wantedRoomBeanList.iterator();
		while (it.hasNext()) {
			if(emaildao.needSendEmail(it.next()) == false) {
				it.remove();
			}
		}
		
		if(wantedRoomBeanList.size() > 0) {
			new Thread(new SendMatchMailUtil(wantedRoomBeanList, roomRentBean)).start();
			for(WantedRoomBean wantedRoomBean : wantedRoomBeanList) {
				emaildao.sendMailAddOne(wantedRoomBean);
			}
		}
	}
	
	@Override
	public void sendMatchEmail(WantedRoomBean wantedRoomBean) {
		List<RoomRentBean> roomRentBeanList = wantedRoomDao.findingMatchRoomAd(wantedRoomBean);
		if(emaildao.needSendEmail(wantedRoomBean) && roomRentBeanList.size() > 0) {
			new Thread(new SendMatchMailUtil(wantedRoomBean, roomRentBeanList)).start();
			emaildao.sendMailAddOne(wantedRoomBean);
		}
		
		Iterator<RoomRentBean> it = roomRentBeanList.iterator();
		while (it.hasNext()) {
			if(emaildao.needSendEmail(it.next()) == false) {
				it.remove();
			}
		}
		
		if(roomRentBeanList.size() > 0) {
			new Thread(new SendMatchMailUtil(roomRentBeanList, wantedRoomBean)).start();
			for (RoomRentBean roomRentBean : roomRentBeanList) {
				emaildao.sendMailAddOne(roomRentBean);
			}
		}
	}
	
	
	
}
