package _4u4u.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import _4u4u.model.InterestedAdForRoomAdBean;
import _4u4u.model.InterestedAdForWantedRoomAdBean;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.SavedAdForRoomAdBean;
import _4u4u.model.SavedAdForWantedRoomAdBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.MemberDao;

// 本類別使用為標準的JDBC技術來存取資料庫。
@Repository
public class MemberDaoImpl implements MemberDao {
	SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public MemberDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();
	}

	// 儲存MemberBean物件，將參數mb新增到Member表格內。
	public int saveMember(MemberBean mb) {
		Session session = factory.getCurrentSession();
		Integer n = 0;
		mb.setState("0"); // 預設為 0:未激活
		session.save(mb);
		n++;
		return n;
	}

	// 判斷參數email(會員帳號)是否已經被現有客戶使用，如果是，傳回true，表示此id不能使用，
	// 否則傳回false，表示此id可使用。
	@Override
	public boolean emailExists(String email) {
		boolean exist = false;
		String hql = "FROM MemberBean WHERE email = :email";
		Session session = factory.getCurrentSession();
		try {
			MemberBean mb = (MemberBean) session.createQuery(hql).setParameter("email", email).getSingleResult();
			if (mb != null) {
				exist = true;
			}
		} catch (NoResultException ex) {
			exist = false;
		} catch (NonUniqueResultException e) {
			exist = true;
		}
		return exist;
	}

	// 由參數 email (會員帳號) 到Member表格中 取得某個會員的所有資料，傳回值為一個MemberBean物件，
	// 如果找不到對應的會員資料，傳回值為null。
	@Override
	public MemberBean loadMemberById(int id) {
		MemberBean mb = null;
		Session session = factory.getCurrentSession();
		try {
			mb = (MemberBean) session.load(MemberBean.class, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mb;
	}

	@Override
	public MemberBean getMemberById(int id) {
		MemberBean mb = null;
		Session session = factory.getCurrentSession();
		try {
			mb = session.get(MemberBean.class, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mb;
	}

	@Override
	public MemberBean getMemberByEmail(String email) {
		MemberBean mb = null;
		String hql = "FROM MemberBean WHERE email = :email";
		Session session = factory.getCurrentSession();

		mb = (MemberBean) session.createQuery(hql).setParameter("email", email).getSingleResult();

		return mb;
	}

	// 檢查使用者在登入時輸入的帳號與密碼是否正確。如果正確，傳回該帳號所對應的MemberBean物件，
	// 否則傳回 null。
	@Override
	public MemberBean checkEmailPassword(String email, String password) {
		MemberBean mb = null;
		Session session = factory.getCurrentSession();
		String hql = "FROM MemberBean WHERE email = :email and password = :password";
		try {
			mb = (MemberBean) session.createQuery(hql).setParameter("email", email).setParameter("password", password)
					.getSingleResult();
		} catch (NoResultException ex) {
			mb = null;
		}
		return mb;
	}

	@Override
	public void delete(int pk) {
		Session session = factory.getCurrentSession();
		MemberBean mb = new MemberBean();
		mb.setMemId(pk);
		session.delete(mb);
		return;
	}

	@Override
	public boolean changeMemberState(String email, String code) {
		MemberBean mb = null;
		Session session = factory.getCurrentSession();
		String hql = "FROM MemberBean WHERE email = :email";
//		String hql2 = "Update MemberBean SET state = :state WHERE email = :email";
		mb = (MemberBean) session.createQuery(hql).setParameter("email", email).getSingleResult();
		mb.setState(code);
//		 mb = session.get(MemberBean.class, mb.getMemId());
		session.update(mb);
		return true;
	}

	@Override
	public String memberState(MemberBean mb) {
		Session session = factory.getCurrentSession();
		String memberState;
		int memId = 0;
		memId = mb.getMemId();
		String hql = "SELECT state FROM MemberBean WHERE memId = :memId";
		memberState = (String) session.createQuery(hql).setParameter("memId", memId).getSingleResult();
		return memberState;
	}

	@Override
	public int saveLikeAd(MemberBean mb, String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();
		Timestamp ts = new Timestamp(new Date().getTime());
		int count = 0;
		try {
			if (adStyle.trim().contentEquals("0")) {
				
				SavedAdForRoomAdBean savedAdForRoomAdBean = new SavedAdForRoomAdBean();
				RoomRentBean targetBean = session.get(RoomRentBean.class, adId);
				if(mb.getMemId()==targetBean.getRoomRentMemId().getMemId()) {
					return -1; // 儲存此廣告與 發佈廣告為同一人
				}
				savedAdForRoomAdBean.setCreateTime(ts);
				savedAdForRoomAdBean.setSavedAdForRoomAdMemId(mb);
				savedAdForRoomAdBean.setSavedAdForRoomAdAdId(targetBean);
				session.save(savedAdForRoomAdBean);
				count++;
			} else {
				SavedAdForWantedRoomAdBean savedAdForWantedRoomAdBean = new SavedAdForWantedRoomAdBean();
				WantedRoomBean targetBean = session.get(WantedRoomBean.class, adId);
				if(mb.getMemId()==targetBean.getWantedRoomAdMemId().getMemId()) {
					return -1; // 儲存此廣告與 發佈廣告為同一人
				}
				savedAdForWantedRoomAdBean.setCreateTime(ts);
				savedAdForWantedRoomAdBean.setSavedAdForWantedRoomAdMemId(mb);
				savedAdForWantedRoomAdBean.setSavedAdForWantedRoomAdFindRoomId(targetBean);
				session.save(savedAdForWantedRoomAdBean);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean cancelSavedAd(MemberBean mb, String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();

		if (adStyle.trim().contentEquals("0")) {
			RoomRentBean roomrentBean = session.get(RoomRentBean.class, adId);
			String hql = "DELETE FROM SavedAdForRoomAdBean s WHERE s.savedAdForRoomAdMemId = :mb AND s.savedAdForRoomAdAdId = :rrb";
			int count = session.createQuery(hql).setParameter("mb", mb).setParameter("rrb", roomrentBean)
					.executeUpdate();
			if (count == 1) {
				return true;
			} else {

				return false;
			}
		} else {
			WantedRoomBean wantedRoomBean = session.get(WantedRoomBean.class, adId);
			String hql = "DELETE FROM SavedAdForWantedRoomAdBean s WHERE s.savedAdForWantedRoomAdMemId = :mb AND s.savedAdForWantedRoomAdFindRoomId = :wrb";
			int count = session.createQuery(hql).setParameter("mb", mb).setParameter("wrb", wantedRoomBean)
					.executeUpdate();
			if (count == 1) {
				return true;
			} else {

				return false;
			}

		}
	}

	@Override
	public String checkAdDetailButtonState(MemberBean mb, String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();
		List<String> list = new ArrayList<String>();
		Gson gson = new Gson();

		if (adStyle.trim().contentEquals("0")) {
			RoomRentBean roomrentBean = session.get(RoomRentBean.class, adId);
			String hql = "FROM SavedAdForRoomAdBean s WHERE s.savedAdForRoomAdMemId = :mb AND s.savedAdForRoomAdAdId = :rrb";
			String hql2 = "FROM InterestedAdForRoomAdBean i WHERE i.interestedAdForRoomAdMemId = :mb AND i.interestedAdForRoomAdAdId = :rrb";
			try {
				session.createQuery(hql).setParameter("mb", mb).setParameter("rrb", roomrentBean).getSingleResult();
				list.add("取消儲存廣告");
			} catch (NoResultException e) {
				list.add("儲存廣告");
			}

			try {
				session.createQuery(hql2).setParameter("mb", mb).setParameter("rrb", roomrentBean).getSingleResult();
				list.add("取消感興趣");
			} catch (NoResultException e) {
				list.add("感興趣");
			}

		} else {
			WantedRoomBean wantedRoomBean = session.get(WantedRoomBean.class, adId);
			String hql = "FROM SavedAdForWantedRoomAdBean s WHERE s.savedAdForWantedRoomAdMemId = :mb AND s.savedAdForWantedRoomAdFindRoomId = :wrb";
			String hq2 = "FROM InterestedAdForWantedRoomAdBean i WHERE i.interestedAdForWantedRoomAdMemId = :mb AND i.interestedAdForWantedRoomAdFindRoomId = :wrb";
			try {
				session.createQuery(hql).setParameter("mb", mb).setParameter("wrb", wantedRoomBean).getSingleResult();
				list.add("取消儲存廣告");

			} catch (NoResultException e) {
				list.add("儲存廣告");
			}

			try {
				session.createQuery(hq2).setParameter("mb", mb).setParameter("wrb", wantedRoomBean).getSingleResult();
				list.add("取消感興趣");
			} catch (NoResultException e) {
				list.add("感興趣");
			}

		}

		return gson.toJson(list);
	}

	@Override
	public int saveInterestedAd(MemberBean mb, String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();
		Timestamp ts = new Timestamp(new Date().getTime());
		int saveInterestedAdCount = 0;
		if (adStyle.trim().contentEquals("0")) {
			RoomRentBean roomrentBean = session.get(RoomRentBean.class, adId);
			if(mb.getMemId()==roomrentBean.getRoomRentMemId().getMemId()) {
				return -1; // 儲存此廣告與 發佈廣告為同一人
			}
			String hql = "FROM SavedAdForRoomAdBean s WHERE s.savedAdForRoomAdMemId = :mb AND s.savedAdForRoomAdAdId = :rrb";
			try {
				session.createQuery(hql).setParameter("mb", mb).setParameter("rrb", roomrentBean).getSingleResult();
			} catch (NoResultException e) {
				SavedAdForRoomAdBean savedAdForRoomAdBean = new SavedAdForRoomAdBean();
				savedAdForRoomAdBean.setCreateTime(ts);
				savedAdForRoomAdBean.setSavedAdForRoomAdMemId(mb);
				savedAdForRoomAdBean.setSavedAdForRoomAdAdId(roomrentBean);
				session.save(savedAdForRoomAdBean);
			}
				session.clear();
				  MemberBean memberBean = session.get(MemberBean.class, mb.getMemId());
				  RoomRentBean roomBean = session.get(RoomRentBean.class, adId);
				InterestedAdForRoomAdBean interestedAdForRoomAdBean = new InterestedAdForRoomAdBean();
				interestedAdForRoomAdBean.setCreateTime(ts);
				interestedAdForRoomAdBean.setInterestedAdForRoomAdMemId(memberBean);
				interestedAdForRoomAdBean.setInterestedAdForRoomAdAdId(roomBean);
				session.save(interestedAdForRoomAdBean);
				saveInterestedAdCount++;

		} else {

			WantedRoomBean wantedRoomBean = session.get(WantedRoomBean.class, adId);
			if(mb.getMemId()==wantedRoomBean.getWantedRoomAdMemId().getMemId()) {
				return -1; // 儲存此廣告與 發佈廣告為同一人
			}
			String hql = "FROM SavedAdForWantedRoomAdBean s WHERE s.savedAdForWantedRoomAdMemId = :mb AND s.savedAdForWantedRoomAdFindRoomId = :wrb";

			try {
				session.createQuery(hql).setParameter("mb", mb).setParameter("wrb", wantedRoomBean).getSingleResult();
			} catch (NoResultException e) {
				SavedAdForWantedRoomAdBean savedAdForWantedRoomAdBean = new SavedAdForWantedRoomAdBean();
				savedAdForWantedRoomAdBean.setCreateTime(ts);
				savedAdForWantedRoomAdBean.setSavedAdForWantedRoomAdMemId(mb);
				savedAdForWantedRoomAdBean.setSavedAdForWantedRoomAdFindRoomId(wantedRoomBean);
				session.save(savedAdForWantedRoomAdBean);
			}
			    session.clear();
			    MemberBean memberBean = session.get(MemberBean.class, mb.getMemId());
			    WantedRoomBean wantedBean = session.get(WantedRoomBean.class, adId);
				InterestedAdForWantedRoomAdBean interestedAdForWantedRoomAdBean = new InterestedAdForWantedRoomAdBean();
				interestedAdForWantedRoomAdBean.setCreateTime(ts);
				interestedAdForWantedRoomAdBean.setInterestedAdForWantedRoomAdMemId(memberBean);
				interestedAdForWantedRoomAdBean.setInterestedAdForWantedRoomAdFindRoomId(wantedBean);
				session.save(interestedAdForWantedRoomAdBean);
				saveInterestedAdCount++;
		}
		return saveInterestedAdCount;
	}

	@Override
	public boolean cancelInterestedAd(MemberBean mb, String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();

		if (adStyle.trim().contentEquals("0")) {
			if (cancelSavedAd(mb, adStyle, adId)) {
				RoomRentBean roomrentBean = session.get(RoomRentBean.class, adId);

				String hql = "DELETE FROM InterestedAdForRoomAdBean i WHERE i.interestedAdForRoomAdMemId = :mb AND i.interestedAdForRoomAdAdId = :rrb";
				int count = session.createQuery(hql).setParameter("mb", mb).setParameter("rrb", roomrentBean)
						.executeUpdate();
				if (count == 1) {
					return true;
				} else {

					return false;
				}

			}
		} else {
			if (cancelSavedAd(mb, adStyle, adId)) {
				WantedRoomBean wantedRoomBean = session.get(WantedRoomBean.class, adId);
				String hql = "DELETE FROM InterestedAdForWantedRoomAdBean i WHERE i.interestedAdForWantedRoomAdMemId = :mb AND i.interestedAdForWantedRoomAdFindRoomId = :wrb";
				int count = session.createQuery(hql).setParameter("mb", mb).setParameter("wrb", wantedRoomBean)
						.executeUpdate();
				if (count == 1) {
					return true;
				} else {
					return false;
				}

			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<String>>  getPotentailBuddyUps(Integer adId) {
		Session session = factory.getCurrentSession();
		List<List<String>> potentailList = new ArrayList<List<String>>();
		List<InterestedAdForRoomAdBean> interestedAdForRoomAdBeanList  = new ArrayList<InterestedAdForRoomAdBean>();
		RoomRentBean roomRentBean = session.get(RoomRentBean.class, adId);
		String hql = " FROM InterestedAdForRoomAdBean i WHERE  i.interestedAdForRoomAdAdId = :rrb";
		interestedAdForRoomAdBeanList = session.createQuery(hql).setParameter("rrb", roomRentBean)
				.getResultList();
		for(InterestedAdForRoomAdBean iBean:interestedAdForRoomAdBeanList) {
			List<WantedRoomBean> wantedRoomBeanList = new ArrayList<WantedRoomBean>();
			List<String> potentailListElement = new ArrayList<String>();
			String hql2 = "FROM WantedRoomBean w WHERE w.wantedRoomAdMemId = :mb AND w.adState = true AND w.agreeShare = true ORDER BY w.adCreateDate DESC";
			wantedRoomBeanList = session.createQuery(hql2).setParameter("mb", iBean.getInterestedAdForRoomAdMemId()).getResultList();
			if(wantedRoomBeanList.size()==0) {
				continue;
			}else {
				potentailListElement.add(iBean.getInterestedAdForRoomAdMemId().getMemId().toString());
				potentailListElement.add(iBean.getInterestedAdForRoomAdMemId().getName());
				potentailListElement.add(wantedRoomBeanList.get(0).getFindRoomId().toString());
				
				potentailList.add(potentailListElement);
			}
		
		}
		
		
		
		return potentailList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<String>> getPotentialWholePropertiesList(Integer memberId) {
		Session session = factory.getCurrentSession();
		List<List<String>> potentailPropertiesList = new ArrayList<List<String>>();
		List<InterestedAdForRoomAdBean> interestedAdForRoomAdBeanList  = new ArrayList<InterestedAdForRoomAdBean>();
		MemberBean memberBean = session.get(MemberBean.class, memberId);
		String hql = " FROM InterestedAdForRoomAdBean i WHERE  i.interestedAdForRoomAdMemId = :mb AND i.interestedAdForRoomAdAdId.adRentType='0'";
		interestedAdForRoomAdBeanList = session.createQuery(hql).setParameter("mb", memberBean)
				.getResultList();
		for(InterestedAdForRoomAdBean iBean:interestedAdForRoomAdBeanList) {
			List<String> potentailListPropertiesElement = new ArrayList<String>();
			String imageFileName = null;
			String imagesString = iBean.getInterestedAdForRoomAdAdId().getAdImages();
			if(imagesString!=null && imagesString.trim().length()!=0) {
				imageFileName = "/disksource/roomRentAd/"+imagesString.split(",")[0];
			}else {
				imageFileName = "${pageContext.request.contextPath}/img/NoImage.png";
			}
			potentailListPropertiesElement.add(iBean.getInterestedAdForRoomAdAdId().getAdId().toString());
			potentailListPropertiesElement.add(imageFileName);
			potentailListPropertiesElement.add(iBean.getInterestedAdForRoomAdAdId().getAdTitle());
				
				potentailPropertiesList.add(potentailListPropertiesElement);
			}
		
		return potentailPropertiesList;
	}

	@Override
	public boolean deleteInterestedAdOnly( String adStyle, Integer adId) {
		Session session = factory.getCurrentSession();
		if (adStyle.trim().contentEquals("0")) {
		 
			
				try {
					String hql = "DELETE FROM InterestedAdForRoomAdBean i WHERE i.id = :id";
					int count = session.createQuery(hql).setParameter("id", adId)
							.executeUpdate();
					if (count == 1) {
						return true;
					} else {

						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					;
				}
			
		} else {
			try {
				String hql = "DELETE FROM InterestedAdForWantedRoomAdBean i WHERE i.id = :id";
				int count = session.createQuery(hql).setParameter("id", adId)
						.executeUpdate();
				if (count == 1) {
					return true;
				} else {

					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				;
			}
		}
		return false;
	}
	
	@Override
	public int updateMember(MemberBean mb) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(mb);
		n++;		
		return n;
	}
}
