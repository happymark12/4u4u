package _4u4u.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;

public interface MessageDao {

	int saveMessage(String from, String to, String info,String title,Timestamp ts);

	List<Map<String, String>> getContacts(MemberBean mb);

	String getHistoryMessage(MemberBean targetMb, MemberBean fromMb);

	int getNewMessageCount(MemberBean longinUser, Integer fromUserId);

	int updateNewMessageCount(MemberBean longinUser, Integer fromUserId);

}
