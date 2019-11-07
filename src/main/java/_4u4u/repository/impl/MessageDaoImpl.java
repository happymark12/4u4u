package _4u4u.repository.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import _4u4u.model.MemberBean;
import _4u4u.model.MessagesBean;
import _4u4u.repository.MemberDao;
import _4u4u.repository.MessageDao;

@Repository
public class MessageDaoImpl implements Serializable, MessageDao{		
	private static final long serialVersionUID = 1L;
	
	SessionFactory factory;
	MemberDao memberDao;
	
	@Autowired	//MVC多了注入實例
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public MessageDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();	//建立工廠
	}

	@Override
	public int saveMessage(String from, String to, String info,String title,Timestamp ts) {
		Session session = factory.getCurrentSession();
		MemberBean mbFrom = memberDao.getMemberByEmail(from.trim());
		MemberBean mbTo = memberDao.getMemberByEmail(to.trim());
		MessagesBean bean = new MessagesBean();
		
		bean.setDetail(info);
		bean.setIsRead(false);
		bean.setSendMemId(mbFrom);
		bean.setTargetMemId(mbTo);
		bean.setSendTime(ts);
		bean.setTitle(title);
		int n = 0;
		session.save(bean);
		n++;
		return n;
	}
	@Override
	public List<Map<String, String>> getContacts(MemberBean mb) {
		Session session = factory.getCurrentSession();
		String hql = "FROM MessagesBean WHERE targetMemId = :mb group by sendMemId";
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a | MM-dd");
		@SuppressWarnings("unchecked")
		List<MessagesBean> list = session.createQuery(hql).setParameter("mb", mb).getResultList();
		 List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();

		for(MessagesBean mBean : list) {
			Map<String, String> strMap = new LinkedHashMap<String, String>();
			String hql2 = "FROM MessagesBean m WHERE m.targetMemId= :tmb and "
					+ " m.sendMemId=:smb "
					+ "and m.sendTime = (SELECT MAX(e.sendTime) FROM MessagesBean e WHERE e.targetMemId= :tmb and " 
					+"  e.sendMemId= :smb) ";
			MessagesBean bean = (MessagesBean) session.createQuery(hql2).setParameter("tmb", mBean.getTargetMemId())
														  .setParameter("smb", mBean.getSendMemId())
														  .getSingleResult();
			
			String hql3 = "SELECT COUNT(*) FROM MessagesBean e WHERE e.targetMemId= :tmb and "
						 +"  e.sendMemId= :smb and isRead=false ";
				
			Long count  =  (Long) session.createQuery(hql3)
						.setParameter("tmb", mBean.getTargetMemId())
					    .setParameter("smb", mBean.getSendMemId())
					    .getSingleResult();
				Date date = new Date(bean.getSendTime().getTime());			
				strMap.put("name",bean.getSendMemId().getName());
				strMap.put("id",bean.getSendMemId().getMemId().toString());
				strMap.put("content",bean.getDetail());
				strMap.put("dateTime",sdf.format(date));
				strMap.put("count",count.toString());
				mapList.add(strMap);
		}
		
		return mapList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getHistoryMessage(MemberBean targetMb, MemberBean fromMb) {
		Session session = factory.getCurrentSession();
		Integer loginUserId = targetMb.getMemId();
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a | MM-dd");
		String hql = "Update MessagesBean SET isRead=true "
				+ "WHERE targetMemId=:tmb and sendMemId=:smb";
		session.createQuery(hql)
				.setParameter("tmb", targetMb)
			    .setParameter("smb", fromMb)
			    .executeUpdate();
		String hql2 = "FROM MessagesBean m WHERE (m.targetMemId= :tmb and "
				+ " m.sendMemId=:smb) OR (m.targetMemId= :smb and " 
				+"	 m.sendMemId=:tmb) order by sendTime";
		List<MessagesBean> list = session.createQuery(hql2)
				.setParameter("tmb", targetMb)
			    .setParameter("smb", fromMb)
			    .getResultList();
		for(MessagesBean bean : list) {
			Map<String, String> strMap = new LinkedHashMap<String, String>();
			if(bean.getSendMemId().getMemId()==loginUserId) {
				
				Date date = new Date(bean.getSendTime().getTime());	
				String dateTime = sdf.format(date);
				if(bean.getIsRead()){
					strMap.put("isRead", "yes");
				}else {
					strMap.put("isRead", "no");
				}
				strMap.put("class","replies");
				strMap.put("content",bean.getDetail());
				strMap.put("dateTime",dateTime);

			}else {
				Date date = new Date(bean.getSendTime().getTime());	
				String time = null;
				time=  sdf.format(date);
				strMap.put("class","sent");
				strMap.put("content",bean.getDetail());
				strMap.put("dateTime",time);
				strMap.put("id",bean.getSendMemId().getMemId().toString());
			}
			mapList.add(strMap);
		}
		Gson gson = new Gson();
		return gson.toJson(mapList);
		
	}
	@Override
	public int getNewMessageCount(MemberBean longinUser, Integer fromUserId) {
		Session session = factory.getCurrentSession();
		MemberBean fromMb = memberDao.getMemberById(fromUserId);
		String hql = "SELECT COUNT(*) FROM MessagesBean e WHERE e.targetMemId= :tmb and "
				 +"  e.sendMemId= :smb and isRead=false ";
		
		Long count  =  (Long) session.createQuery(hql)
				.setParameter("tmb", longinUser)
			    .setParameter("smb", fromMb)
			    .getSingleResult();
		
		return Integer.parseInt(count.toString());
	}
	@Override
	public int updateNewMessageCount(MemberBean longinUser, Integer fromUserId) {
		Session session = factory.getCurrentSession();
		MemberBean fromMb = memberDao.getMemberById(fromUserId);
		String hql = "Update MessagesBean SET isRead=true "
				+ "WHERE targetMemId=:tmb and sendMemId=:smb";
		int n = session.createQuery(hql)
				.setParameter("tmb", longinUser)
			    .setParameter("smb", fromMb)
			    .executeUpdate();
		
		return n;
	}
		
		
		
		
}

	

