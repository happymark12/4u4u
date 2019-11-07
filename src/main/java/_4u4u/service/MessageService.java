package _4u4u.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;

public interface MessageService {
	
	
	public int saveMessage(String from,String to,String info,String title,Timestamp ts);

	public List<Map<String, String>> getContacts(MemberBean mb);

	public String getHistoryMessage(MemberBean targetMb, MemberBean fromMb);
	
	public int getNewMessageCount(MemberBean longinUser,Integer fromUserId);

	public int updateNewMessageCount(MemberBean longinUser,Integer fromUserId);
}
