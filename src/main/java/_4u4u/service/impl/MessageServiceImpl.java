package _4u4u.service.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.EmailDao;
import _4u4u.repository.MemberDao;
import _4u4u.repository.MessageDao;
import _4u4u.repository.RoomRentDao;
import _4u4u.repository.WantedRoomDao;
import _4u4u.service.MessageService;
import _4u4u_init.util.SendMatchMailUtil;

@Transactional
@Service
public class MessageServiceImpl implements MessageService {
	MessageDao messageDao;
	
	
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	


	@Override
	public int saveMessage(String from, String to, String info,String title,Timestamp ts) {
		
		return messageDao.saveMessage(from,to,info,title,ts);
	}




	@Override
	public List<Map<String, String>> getContacts(MemberBean mb) {
		return messageDao.getContacts(mb);
	}




	@Override
	public String getHistoryMessage(MemberBean targetMb, MemberBean fromMb) {
		return messageDao.getHistoryMessage(targetMb,fromMb);
	}




	@Override
	public int getNewMessageCount(MemberBean longinUser, Integer fromUserId) {
		return messageDao.getNewMessageCount(longinUser,fromUserId);
	}




	@Override
	public int updateNewMessageCount(MemberBean longinUser, Integer fromUserId) {
		return messageDao.updateNewMessageCount(longinUser,fromUserId);
	}

	
}
