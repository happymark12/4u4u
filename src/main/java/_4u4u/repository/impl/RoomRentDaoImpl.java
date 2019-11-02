package _4u4u.repository.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _4u4u.model.InterestedAdForRoomAdBean;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.RoomRentDao;
import _4u4u_init.util.ConvertTableUtil;

// 本類別使用純JDBC的技術來存取資料庫。
// 所有SQLException都以catch區塊捕捉，然後一律再次丟出RuntimeException。
// 對SQLException而言，即使catch下來，程式依然無法正常執行，所以捕捉SQLException，再次丟出
// RuntimeException。
@Repository
public class RoomRentDaoImpl implements Serializable, RoomRentDao {
	private static final long serialVersionUID = 1L;

	private int pageNo = 1; // 存放目前顯示頁面的編號
	private int recordsPerPage = 6;// 每頁顯示六則廣告
	private int totalPages = -1;
	SessionFactory factory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public RoomRentDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public int saveRoomRentAd(RoomRentBean bean) {
		Session session = factory.getCurrentSession();
		Integer n = 0;
		session.save(bean);
		n++;
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getAllRoomRentAd() {
		Session session = factory.getCurrentSession();
		String hql = "FROM RoomRentBean";
		List<RoomRentBean> list = null;
		list = session.createQuery(hql).getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getVIPOnlyAds() {
		Session session = factory.getCurrentSession();
		String hql = "FROM RoomRentBean r WHERE r.roomRentMemId.state='2' AND r.adState = true";
		List<RoomRentBean> list = null;
		list = session.createQuery(hql).getResultList();

		return list;
	}

	@Override
	public RoomRentBean getAdById(Integer id) {
		RoomRentBean bean = null;
		Session session = factory.getCurrentSession();
		try {
			bean = session.get(RoomRentBean.class, id);
			bean.getRoomItems().iterator();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bean;
	}

	@Override
	public boolean isExistEffectiveAd(MemberBean mb) {
		Session session = factory.getCurrentSession();
		boolean isExistEffectiveAd = false;
		String hql = "SELECT adState FROM RoomRentBean WHERE roomRentMemId = :mb";
		@SuppressWarnings("unchecked")
		List<Boolean> list = session.createQuery(hql).setParameter("mb", mb).getResultList();
		for (Boolean flag : list) {
			if (flag) {
				isExistEffectiveAd = true;
			}
		}
		return isExistEffectiveAd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getAjaxSearchResultByPage(Map<String, String> conditionMap) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		Set<String> conditionKeys = conditionMap.keySet();
		if (conditionKeys.contains("sortOption")) {
			if (conditionMap.get("sortOption").contentEquals("1")||conditionMap.get("sortOption").contentEquals("2")) {
				hqlBuffer.append("FROM RoomRentBean  r  JOIN  RoomBean  j " + " on   j.roomAd.adId = r.adId "
						+ "WHERE r.adState = true ");
			}
		
			if (!conditionMap.get("sortOption").contentEquals("2")
					&& !conditionMap.get("sortOption").contentEquals("1")) {
				hqlBuffer.append("FROM RoomRentBean r WHERE r.adState = true");
			}
		} else {
			hqlBuffer.append("FROM RoomRentBean r WHERE r.adState = true");

		}
		if (conditionKeys.contains("smoke")) {
			hqlBuffer.append(" AND r.adFutureSmoke = " + conditionMap.get("smoke"));
		}
		if (conditionKeys.contains("pet")) {
			hqlBuffer.append(" AND r.adFuturePet = " + conditionMap.get("pet"));
		}

		if (conditionKeys.contains("availableDate")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date test = sdf.parse(conditionMap.get("availableDate"));

				Calendar c = Calendar.getInstance();
				c.setTime(test);
				c.add(Calendar.DATE, 21);
				Date dateEnd = c.getTime();
				c.add(Calendar.DATE, -42);
				Date dateStart = c.getTime();
				String dateBeginStr = sdf.format(dateStart);
				String dateEndStr = sdf.format(dateEnd);
				hqlBuffer.append(" AND r.adAvailableDate BETWEEN '" + dateBeginStr + "' AND '" + dateEndStr + "'");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (conditionKeys.contains("roomNum")) {
			hqlBuffer.append(" AND r.adRoomNum >= " + conditionMap.get("roomNum"));
		}

		List<String> rentTypeList = null;
		if (conditionKeys.contains("rentType0") || conditionKeys.contains("rentType1")
				|| conditionKeys.contains("rentType2") || conditionKeys.contains("rentType3")) {
			rentTypeList = new ArrayList<String>();
			for (int i = 0; i < 4; i++) {
				if (conditionKeys.contains("rentType" + i)) {
					rentTypeList.add("" + i);

				}

			}
			hqlBuffer.append(" AND r.adRentType IN (:rentTypeList)");
		}

		if (conditionKeys.contains("gender")) {
			if (conditionMap.get("gender").contentEquals("0")) {

				hqlBuffer.append(" AND r.adCurGender IS NOT NULL AND locate('女'  ,r.adCurGender ) =0");
			} else {
				hqlBuffer.append(" AND r.adCurGender IS NOT NULL AND locate('男'   ,r.adCurGender) =0");

			}

		}

		if (conditionKeys.contains("district")) {

			String area = conditionMap.get("county").trim() + conditionMap.get("district").trim();
			String decodeArea = null;
			try {
				decodeArea = URLDecoder.decode(area.trim(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			System.out.println(decodeArea);
			String areaCode = ConvertTableUtil.addressStringToCode(decodeArea.trim());
			hqlBuffer.append(" AND r.adAreacode = '" + areaCode + "'");

		} else if (!conditionKeys.contains("district") && conditionKeys.contains("county")) {
			String county = conditionMap.get("county");
			String decodeCounty = null;
			try {
				decodeCounty = URLDecoder.decode(county.trim(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			System.out.println(decodeCounty);
			String countyCode = ConvertTableUtil.addressStringToCode(decodeCounty.trim());
			if (countyCode.length() == 1) {
				countyCode = "0" + countyCode.trim();
			}

			hqlBuffer.append(" AND SUBSTRING(concat('',lpad(r.adAreacode,4,'0')),1,2) ='" + countyCode + "'");
		}

		if (!conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND (SELECT MIN(b.rentPrice) FROM RoomBean b WHERE b.roomAd.adId=r.adId) <" + priceMax);
		}

		if (conditionKeys.contains("rentPriceMin") && !conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");

			hqlBuffer.append(" AND (SELECT MAX(b.rentPrice) FROM RoomBean b WHERE b.roomAd.adId=r.adId) >" + priceMin);
		}
		if (conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND exists(FROM RoomBean b WHERE b.roomAd.adId=r.adId AND b.rentPrice BETWEEN "
					+ priceMin + " AND " + priceMax + ")");
		}
		if (conditionKeys.contains("sortOption")) {
			if (conditionMap.get("sortOption").contentEquals("1")) {
				hqlBuffer.append(" group by r.adId  ORDER BY Max(j.rentPrice) ");
			}
			if (conditionMap.get("sortOption").contentEquals("2")) {
				hqlBuffer.append(" group by r.adId  ORDER BY Max(j.rentPrice) DESC");
			}
			if (conditionMap.get("sortOption").contentEquals("3")) {
				hqlBuffer.append(" ORDER BY r.adCreateDate DESC");
			}
			if (conditionMap.get("sortOption").contentEquals("4")) {
				hqlBuffer.append(" ORDER BY r.adUpdateDate DESC");
			}
		}
//		System.out.println(hqlBuffer.toString());
		List<RoomRentBean> list = new ArrayList<RoomRentBean>();
		List<Object[]> ojbArrayList = null;
		if (conditionKeys.contains("rentType0") || conditionKeys.contains("rentType1")
				|| conditionKeys.contains("rentType2") || conditionKeys.contains("rentType3")) {
			if (conditionKeys.contains("sortOption")) {
				if (conditionMap.get("sortOption").contentEquals("1")||conditionMap.get("sortOption").contentEquals("2")) {
					
					ojbArrayList =  session.createQuery(hqlBuffer.toString()).setParameterList("rentTypeList", rentTypeList)
							.setFirstResult((Integer.parseInt(conditionMap.get("curPage").trim()) - 1) * 6)
							.setMaxResults(6).getResultList();
					for(Object[] objArray: ojbArrayList) {
						for(Object obj :objArray) {
							if(obj instanceof RoomRentBean) {
								RoomRentBean bean = (RoomRentBean)obj;
								list.add(bean);
							}
							
							
							
						}
						
					}
				
				}
				

				if (!conditionMap.get("sortOption").contentEquals("2")
						&& !conditionMap.get("sortOption").contentEquals("1")) {
					list = session.createQuery(hqlBuffer.toString()).setParameterList("rentTypeList", rentTypeList)
							.setFirstResult((Integer.parseInt(conditionMap.get("curPage").trim()) - 1) * 6)
							.setMaxResults(6).getResultList();

				}

			} else {
				list = session.createQuery(hqlBuffer.toString()).setParameterList("rentTypeList", rentTypeList)
						.setFirstResult((Integer.parseInt(conditionMap.get("curPage").trim()) - 1) * 6).setMaxResults(6)
						.getResultList();
			}

		} else {

			list = new ArrayList<RoomRentBean>();

		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getAjaxSearchResultTotalPages(Map<String, String> conditionMap) {

		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("SELECT COUNT(*) FROM RoomRentBean r WHERE r.adState = true");
		Set<String> conditionKeys = conditionMap.keySet();
		if (conditionKeys.contains("smoke")) {
			hqlBuffer.append(" AND r.adFutureSmoke = " + conditionMap.get("smoke"));
		}
		if (conditionKeys.contains("pet")) {
			hqlBuffer.append(" AND r.adFuturePet = " + conditionMap.get("pet"));
		}

		if (conditionKeys.contains("availableDate")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date test = sdf.parse(conditionMap.get("availableDate"));

				Calendar c = Calendar.getInstance();
				c.setTime(test);
				c.add(Calendar.DATE, 21);
				Date dateEnd = c.getTime();
				c.add(Calendar.DATE, -42);
				Date dateStart = c.getTime();
				String dateBeginStr = sdf.format(dateStart);
				String dateEndStr = sdf.format(dateEnd);
				hqlBuffer.append(" AND r.adAvailableDate BETWEEN '" + dateBeginStr + "' AND '" + dateEndStr + "'");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (conditionKeys.contains("roomNum")) {
			hqlBuffer.append(" AND r.adRoomNum >= " + conditionMap.get("roomNum"));
		}

		List<String> rentTypeList = null;
		if (conditionKeys.contains("rentType0") || conditionKeys.contains("rentType1")
				|| conditionKeys.contains("rentType2") || conditionKeys.contains("rentType3")) {
			rentTypeList = new ArrayList<String>();
			for (int i = 0; i < 4; i++) {
				if (conditionKeys.contains("rentType" + i)) {
					rentTypeList.add("" + i);

				}

			}
			hqlBuffer.append(" AND r.adRentType IN (:rentTypeList)");
		}

		if (conditionKeys.contains("gender")) {
			if (conditionMap.get("gender").contentEquals("0")) {

				hqlBuffer.append(" AND r.adCurGender IS NOT NULL AND locate('女' ,r.adCurGender) =0");
			} else {
				hqlBuffer.append(" AND r.adCurGender IS NOT NULL AND locate('男' ,r.adCurGender) =0");

			}

		}

		if (conditionKeys.contains("district")) {

			String area = conditionMap.get("county").trim() + conditionMap.get("district").trim();
			String decodeArea = null;
			try {
				decodeArea = URLDecoder.decode(area.trim(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			System.out.println(decodeArea);
			String areaCode = ConvertTableUtil.addressStringToCode(decodeArea.trim());
			hqlBuffer.append(" AND r.adAreacode = '" + areaCode + "'");

		} else if (!conditionKeys.contains("district") && conditionKeys.contains("county")) {
			String county = conditionMap.get("county");
			String decodeCounty = null;
			try {
				decodeCounty = URLDecoder.decode(county.trim(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String countyCode = ConvertTableUtil.addressStringToCode(decodeCounty.trim());
			if (countyCode.length() == 1) {
				countyCode = "0" + countyCode.trim();
			}

			hqlBuffer.append(" AND SUBSTRING(concat('',lpad(r.adAreacode,4,'0')),1,2) ='" + countyCode + "'");
		}

		if (!conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND (SELECT MIN(b.rentPrice) FROM RoomBean b WHERE b.roomAd.adId=r.adId) <" + priceMax);
		}

		if (conditionKeys.contains("rentPriceMin") && !conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");

			hqlBuffer.append(" AND (SELECT MAX(b.rentPrice) FROM RoomBean b WHERE b.roomAd.adId=r.adId) >" + priceMin);
		}
		if (conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND exists(FROM RoomBean b WHERE b.roomAd.adId=r.adId AND b.rentPrice BETWEEN "
					+ priceMin + " AND " + priceMax + ")");
		}

		List<Long> list = null;
		if (conditionKeys.contains("rentType0") || conditionKeys.contains("rentType1")
				|| conditionKeys.contains("rentType2") || conditionKeys.contains("rentType3")) {

			list = session.createQuery(hqlBuffer.toString()).setParameterList("rentTypeList", rentTypeList)
					.getResultList();

		} else {

			list = new ArrayList<Long>();
			list.add(0l);

		}
		Integer totoalPages = (int) Math.ceil(list.get(0) / 6.0);
		return totoalPages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WantedRoomBean> findingMatchWantedRoomAd(RoomRentBean roomRentBean) {
		Session session = factory.getCurrentSession();
		List<WantedRoomBean> list = null;
		int suiteQuantity = 0;
		int roomQuantity = 0;
		// 找出廣告的套雅房數量
		for (RoomBean roomBean : roomRentBean.getRoomItems()) {
			if (roomBean.getRoomType().equals("0")) {
				suiteQuantity++;
			} else if (roomBean.getRoomType().equals("1")) {
				roomQuantity++;
			}
		}
		// hql 條件判斷 + 字串串接
		String hql = "FROM WantedRoomBean WHERE wantedRoomAdMemId != " + roomRentBean.getRoomRentMemId().getMemId();
		StringBuffer sbf = new StringBuffer(" AND adState = 1");
//		sbf.append(" AND areaCode = " + roomRentBean.getAdAreacode());
		sbf.append(" AND suiteQuantity <= " + suiteQuantity);
		sbf.append(" AND roomQuantity <= " + roomQuantity);
		if (roomRentBean.getAdCurGender() == "0") {
			sbf.append(" AND wantedRoommatesGender = 0");
		} else if (roomRentBean.getAdCurGender() == "1") {
			sbf.append(" AND wantedRoommatesGender = 1");
		}
		if (roomRentBean.getAdFutureJobType() == "0") {
			sbf.append(" AND job = 0");
		} else if (roomRentBean.getAdFutureJobType() == "1") {
			sbf.append(" AND job = 1");
		}
		if (roomRentBean.getAdCurSmoke() != null) {
			if (roomRentBean.getAdCurSmoke() == true) {
				sbf.append(" AND wantedRoommatesSmoke = true");
			} else if (roomRentBean.getAdCurSmoke() == false) {
				sbf.append(" AND wantedRoommatesSmoke = false");
			}
		}
		if (roomRentBean.getAdCurHasPet() != null) {
			if (roomRentBean.getAdCurHasPet() == true) {
				sbf.append(" AND wantedRoommatesPet = true");
			} else if (roomRentBean.getAdCurHasPet() == false) {
				sbf.append(" AND wantedRoommatesPet = false");
			}
		}
		if (roomRentBean.getAdFutureSmoke() != null) {
			if (roomRentBean.getAdFutureSmoke() == false) {
				sbf.append(" AND allowSmoke = false");
			}
		}
		if (roomRentBean.getAdFuturePet() != null) {
			if (roomRentBean.getAdFuturePet() == false) {
				sbf.append(" AND allowPet = false");
			}
		}

		hql += sbf;
		list = (List<WantedRoomBean>) session.createQuery(hql).getResultList();

		Iterator<WantedRoomBean> it = list.iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unused")
			boolean flag = false;
			String[] areaCodes = it.next().getAreaCode().trim().split(",");
			for (String areaCode : areaCodes) {
				if (areaCode.equals(roomRentBean.getAdAreacode())) {
					flag = true;
				}
			}

			if (flag = false) {
				it.remove();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getAjaxSavedAdsByPage(MemberBean mb, Integer curPage, String sortOption) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append(
				"SELECT s.savedAdForRoomAdAdId FROM SavedAdForRoomAdBean  s WHERE s.savedAdForRoomAdMemId = :mb "
						+ "AND s.savedAdForRoomAdAdId.adState = true");

		if (sortOption.contentEquals("3")) {
			hqlBuffer.append(" ORDER BY s.savedAdForRoomAdAdId.adCreateDate DESC");
		}
		if (sortOption.contentEquals("4")) {
			hqlBuffer.append(" ORDER BY s.savedAdForRoomAdAdId.adUpdateDate DESC");
		}

		List<RoomRentBean> list = null;

		list = session.createQuery(hqlBuffer.toString()).setParameter("mb", mb).setFirstResult((curPage - 1) * 6)
				.setMaxResults(6).getResultList();

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getAjaxSavedAdsTotalPages(MemberBean mb, String sortOption) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("SELECT COUNT(*) FROM SavedAdForRoomAdBean  s WHERE s.savedAdForRoomAdMemId = :mb "
				+ "AND s.savedAdForRoomAdAdId.adState = true");

		List<Long> list = null;
		list = session.createQuery(hqlBuffer.toString()).setParameter("mb", mb).getResultList();

		Integer totoalPages = (int) Math.ceil(list.get(0) / 6.0);
		return totoalPages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<Object>> getAjaxInterestedAdsByPage(MemberBean mb, Integer curPage) {
		Session session = factory.getCurrentSession();
		String hql = "FROM InterestedAdForRoomAdBean  i WHERE i.interestedAdForRoomAdAdId.adState=true"
				+ " AND i.interestedAdForRoomAdAdId.roomRentMemId = :mb"
				+ " AND exists(FROM WantedRoomBean b WHERE b.adState=true "
				+ " AND b.wantedRoomAdMemId = i.interestedAdForRoomAdMemId)";
		String hql2 = "FROM WantedRoomBean WHERE wantedRoomAdMemId = :mb AND adState=true";
		List<List<Object>> objList = new ArrayList<List<Object>>();
		List<InterestedAdForRoomAdBean> list = null;

		list = session.createQuery(hql).setParameter("mb", mb).setFirstResult((curPage - 1) * 6).setMaxResults(6)
				.getResultList();

		if (list != null && list.size() > 0) {
			for (InterestedAdForRoomAdBean bean : list) {
				List<Object> objListElement = new ArrayList<Object>();
				MemberBean memberBean = bean.getInterestedAdForRoomAdMemId();
				WantedRoomBean wantedBean = (WantedRoomBean) session.createQuery(hql2).setParameter("mb", memberBean)
						.getResultList().get(0);
				objListElement.add(wantedBean);
				objListElement.add(bean.getInterestedAdForRoomAdAdId().getAdTitle());
				objListElement.add(bean.getId());
				objList.add(objListElement);
			}
		}

		return objList;
	}

	@Override
	public Integer getAjaxInterestedAdsTotalPages(MemberBean mb) {

		Session session = factory.getCurrentSession();
		String hql = "SELECT count(*) FROM InterestedAdForRoomAdBean  i WHERE i.interestedAdForRoomAdAdId.adState=true"
				+ " AND i.interestedAdForRoomAdAdId.roomRentMemId = :mb"
				+ " AND exists(FROM WantedRoomBean b WHERE b.adState=true "
				+ " AND b.wantedRoomAdMemId = i.interestedAdForRoomAdMemId)";
		Long count = null;

		count = (Long) session.createQuery(hql).setParameter("mb", mb).getSingleResult();

		Integer totoalPages = (int) Math.ceil(count / 6.0);
		return totoalPages;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	@Override
	public int getMyAdTotalPagesByFk(MemberBean roomRentMemId) {
		totalPages = (int) (Math.ceil(getMyAdRecordCountsByFk(roomRentMemId) / (double) recordsPerPage));
		if (totalPages <= 0) {
			totalPages = 1;
		}
		return totalPages;
	}

	@Override
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public long getMyAdRecordCountsByFk(MemberBean roomRentMemId) {
		Long count = 0L; // 必須使用 long 型態
		String hql = "SELECT count(*) FROM RoomRentBean WHERE roomRentMemId = :roomRentMemId";
		Session session = factory.getCurrentSession();
		count = (Long) session.createQuery(hql).setParameter("roomRentMemId", roomRentMemId).getSingleResult();
		return count;
	}

	@Override
	public int deleteRoomRentAd(Integer id) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		RoomRentBean bean = session.get(RoomRentBean.class, id);
		session.delete(bean);
		n++;
		return n;
	}

	@Override
	public int updateRoomRentAd(RoomRentBean bean) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(bean);
		n++;
		return n;
	}

	@Override
	public int changeAdState(RoomRentBean bean) {
		int n = 0;
		Session session = factory.getCurrentSession();
		bean.setAdState(!bean.getAdState());
		session.saveOrUpdate(bean);
		n++;
		return n;
	}

	// 查詢某一頁的廣告，執行本方法前，一定要先設定實例變數pageNo的初值
	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getPageRoomRentAds() {
		List<RoomRentBean> list = new ArrayList<>();
		Session session = factory.getCurrentSession();
		String hql = "FROM RoomRentBean";
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		list = session.createQuery(hql).setFirstResult(startRecordNo).setMaxResults(recordsPerPage).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> getPageAdByFk(int pageNo, MemberBean roomRentMemId) {
		List<RoomRentBean> list = null;
		Session session = factory.getCurrentSession();
		String hql = "FROM RoomRentBean WHERE roomRentMemId = :roomRentMemId";
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		list = session.createQuery(hql).setParameter("roomRentMemId", roomRentMemId).setFirstResult(startRecordNo)
				.setMaxResults(recordsPerPage).getResultList();
		// 解決lazyloading 讓他繼續往roomItems找
		for (RoomRentBean bean : list) {
			Iterator<RoomBean> iterator = bean.getRoomItems().iterator();
		}
		return list;
	}
}
