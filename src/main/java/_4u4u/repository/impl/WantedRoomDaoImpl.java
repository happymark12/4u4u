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

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import _4u4u.model.InterestedAdForWantedRoomAdBean;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.WantedRoomDao;
import _4u4u_init.util.ConvertTableUtil;

//本類別負責讀取資料庫內WantedRoom表格內的紀錄
@Repository
public class WantedRoomDaoImpl implements Serializable, WantedRoomDao {
	private static final long serialVersionUID = 1L;

	private int pageNo = 1; // 存放目前顯示頁面的編號
	private int recordsPerPage = 6;// 每頁顯示六則資料
	private int totalPages = -1;
	SessionFactory factory;
	
	@Autowired
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public WantedRoomDaoImpl() {
//		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public WantedRoomBean getAdById(Integer id) {
		WantedRoomBean bean = null;
		Session session = factory.getCurrentSession();
		try {
			bean = session.get(WantedRoomBean.class, id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bean;
	}

	@Override
	public int saveFindRoomAd(WantedRoomBean wantedRoomBean) {

		Session session = factory.getCurrentSession();
		int n = 0;

		session.save(wantedRoomBean);
		n++;
		return n;
	}

	@Override
	public boolean isExistAd(MemberBean mb) {
		Session session = factory.getCurrentSession();
		boolean isExistAd = false;
		String hql = "SELECT COUNT(*) FROM WantedRoomBean WHERE wantedRoomAdMemId = :mb GROUP BY wantedRoomAdMemId";
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) session.createQuery(hql).setParameter("mb", mb).getResultList();
		if (list.size() > 0) {
			if (list.get(0) >= 1) {
				isExistAd = true;
			}
		}
		return isExistAd;
	}
	@Override
	public boolean isExistEffectiveAd(MemberBean mb) {
		Session session = factory.getCurrentSession();
		String hql = " FROM WantedRoomBean WHERE wantedRoomAdMemId = :mb AND adState=true";
		boolean isExistEffectiveAd = true;
		try {
		session.createQuery(hql).setParameter("mb",	mb).getSingleResult();
		} catch (NoResultException e) {
			isExistEffectiveAd = false;
		}
		return isExistEffectiveAd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WantedRoomBean> getAjaxSearchResultByPage(Map<String, String> conditionMap) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("FROM WantedRoomBean w WHERE w.adState = true");
		Set<String> conditionKeys = conditionMap.keySet();
		if (conditionKeys.contains("smoke")) {
			hqlBuffer.append(" AND w.wantedRoommatesSmoke = " + conditionMap.get("smoke"));
		}
		if (conditionKeys.contains("pet")) {
			hqlBuffer.append(" AND w.wantedRoommatesPet = " + conditionMap.get("pet"));
		}

		if (conditionKeys.contains("searchType")) {

			if (conditionMap.get("searchType").contentEquals("1")) {
				hqlBuffer.append(" AND w.agreeShare = false");

			} else {

				hqlBuffer.append(" AND w.agreeShare = true");
			}
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
				hqlBuffer.append(" AND w.checkInDate BETWEEN '" + dateBeginStr + "' AND '" + dateEndStr + "'");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (conditionKeys.contains("roomNum")) {
			if (conditionMap.get("roomNum").contentEquals("1")) {

				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity = 1 ");
			} else if (conditionMap.get("roomNum").contentEquals("2")) {
				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity >= 2 ");

			} else {
				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity >= 3 ");
			}
		}

		if (conditionKeys.contains("gender")) {
			if (conditionMap.get("gender").contentEquals("0")) {

				hqlBuffer.append(" AND w.peopleNumGender IS NOT NULL AND locate('女'  ,  w.peopleNumGender) = 0");
			} else {
				hqlBuffer.append(" AND w.peopleNumGender IS NOT NULL AND locate('男' , w.peopleNumGender ) = 0");

			}

		}

		if (!conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND w.budget <" + priceMax);
		}

		if (conditionKeys.contains("rentPriceMin") && !conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");

			hqlBuffer.append(" AND w.budget >=" + priceMin);
		}
		if (conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND w.budget BETWEEN " + priceMin + " AND " + priceMax);
		}

		if (conditionKeys.contains("sortOption")) {
			if (conditionMap.get("sortOption").contentEquals("1")) {
				hqlBuffer.append(" ORDER BY w.budget ");
			}
			if (conditionMap.get("sortOption").contentEquals("2")) {
				hqlBuffer.append(" ORDER BY w.budget DESC");
			}
			if (conditionMap.get("sortOption").contentEquals("3")) {
				hqlBuffer.append(" ORDER BY w.adCreateDate DESC");
			}
			if (conditionMap.get("sortOption").contentEquals("4")) {
				hqlBuffer.append(" ORDER BY w.adUpdateDate DESC");
			}
		}
		List<WantedRoomBean> list = null;
		List<WantedRoomBean> finalList = new ArrayList<WantedRoomBean>();
		List<WantedRoomBean> finalListByPage = new ArrayList<WantedRoomBean>();
		
		list = session.createQuery(hqlBuffer.toString())
				.getResultList();
		if (list.size() != 0) {
			if (conditionKeys.contains("district")) {

				String area = conditionMap.get("county").trim() + conditionMap.get("district").trim();
				String decodeArea = null;
				try {
					decodeArea = URLDecoder.decode(area.trim(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String areaCode = ConvertTableUtil.addressStringToCode(decodeArea.trim());
				for (WantedRoomBean bean : list) {

					if (bean.getAreaCode() != null) {
						if (bean.getAreaCode().trim().contains(areaCode.trim())) {
							finalList.add(bean);
						}

					} else {
						continue;
					}
				}

				

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
				
				for (WantedRoomBean bean : list) {
					String[] areaCodeTempArray = null;
					if (bean.getAreaCode() != null) {
						areaCodeTempArray = bean.getAreaCode().trim().split(",");
						for (int i =0;i<areaCodeTempArray.length;i++) {
							String areaCode = areaCodeTempArray[i];
							
							if (areaCode.length() < 3) {
								if (countyCode.trim().contentEquals("00")) {
									finalList.add(bean);
									break;
								}
							} else if (areaCode.length() == 3) {
								if (countyCode.trim().contentEquals("0" + areaCode.trim().substring(0, 1))) {
									finalList.add(bean);
									break;
								}

							} else {
								if (countyCode.trim().contentEquals(areaCode.trim().substring(0, 2))) {
									finalList.add(bean);
									break;
								}
							}

						}
					}
				}
				
			} else if (!conditionKeys.contains("district") && !conditionKeys.contains("county")) {
				finalList = list;
			}
		} else {
			finalList = list;
		}
		int curPage = Integer.parseInt(conditionMap.get("curPage").trim());
		int indexStart = (curPage-1)*6;
		for(int i = indexStart; i<curPage*6;i++) {
			if(i<finalList.size()) {
				
				finalListByPage.add(finalList.get(i));
				
			}else {
				break;
			}
			
			
		}
		
		
		
		
		return finalListByPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getAjaxSearchResultTotalPages(Map<String, String> conditionMap) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("FROM WantedRoomBean w WHERE w.adState = true");
		Set<String> conditionKeys = conditionMap.keySet();
		if (conditionKeys.contains("smoke")) {
			hqlBuffer.append(" AND w.wantedRoommatesSmoke = " + conditionMap.get("smoke"));
		}
		if (conditionKeys.contains("pet")) {
			hqlBuffer.append(" AND w.wantedRoommatesPet = " + conditionMap.get("pet"));
		}

		if (conditionKeys.contains("searchType")) {

			if (conditionMap.get("searchType").contentEquals("1")) {
				hqlBuffer.append(" AND w.agreeShare = false");

			} else {

				hqlBuffer.append(" AND w.agreeShare = true");
			}
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
				hqlBuffer.append(" AND w.checkInDate BETWEEN '" + dateBeginStr + "' AND '" + dateEndStr + "'");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (conditionKeys.contains("roomNum")) {
			if (conditionMap.get("roomNum").contentEquals("1")) {

				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity = 1 ");
			} else if (conditionMap.get("roomNum").contentEquals("2")) {
				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity >= 2 ");

			} else {
				hqlBuffer.append(" AND w.suiteQuantity + w.roomQuantity >= 3 ");
			}
		}

		if (conditionKeys.contains("gender")) {
			if (conditionMap.get("gender").contentEquals("0")) {

	hqlBuffer.append(" AND w.peopleNumGender IS NOT NULL AND locate( '女', w.peopleNumGender )  = 0 ");
			} else {
	hqlBuffer.append(" AND w.peopleNumGender IS NOT NULL AND locate('男', w.peopleNumGender )  = 0 ");

			}

		}

		if (!conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND w.budget <" + priceMax);
		}

		if (conditionKeys.contains("rentPriceMin") && !conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");

			hqlBuffer.append(" AND w.budget >=" + priceMin);
		}
		if (conditionKeys.contains("rentPriceMin") && conditionKeys.contains("rentPriceMax")) {
			String priceMin = conditionMap.get("rentPriceMin");
			String priceMax = conditionMap.get("rentPriceMax");

			hqlBuffer.append(" AND w.budget BETWEEN " + priceMin + " AND " + priceMax);
		}
		List<WantedRoomBean> list = null;
		List<WantedRoomBean> finalList = new ArrayList<WantedRoomBean>();
		list = session.createQuery(hqlBuffer.toString())
				.getResultList();
		
		if (list.size() != 0) {
			if (conditionKeys.contains("district")) {

				String area = conditionMap.get("county").trim() + conditionMap.get("district").trim();
				String decodeArea = null;
				try {
					decodeArea = URLDecoder.decode(area.trim(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String areaCode = ConvertTableUtil.addressStringToCode(decodeArea.trim());
				for (WantedRoomBean bean : list) {

					if (bean.getAreaCode() != null) {
						if (bean.getAreaCode().trim().contains(areaCode.trim())) {
							finalList.add(bean);
						}

					} else {
						continue;
					}
				}

				

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

				for (WantedRoomBean bean : list) {
					String[] areaCodeTempArray = null;
					if (bean.getAreaCode() != null) {
						areaCodeTempArray = bean.getAreaCode().trim().split(",");
						for (String areaCode : areaCodeTempArray) {

							if (areaCode.length() < 3) {
								if (countyCode.trim().contentEquals("0")) {
									finalList.add(bean);
									break;
								}
							} else if (areaCode.length() == 3) {
								if (countyCode.trim().contentEquals("0" + areaCode.trim().substring(0, 1))) {
									finalList.add(bean);
									break;
								}

							} else {
								if (countyCode.trim().contentEquals(areaCode.trim().substring(0, 2))) {
									finalList.add(bean);
									break;
								}
							}

						}
					}
				}
				
			} else if (!conditionKeys.contains("district") && !conditionKeys.contains("county")) {
				finalList = list;
			}
		} else {
			finalList = list;
		}
		return  (int)(Math.ceil(finalList.size()/6.0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoomRentBean> findingMatchRoomAd(WantedRoomBean wantedRoomBean){
		Session session = factory.getCurrentSession();
		List<RoomRentBean> list = null;
		int suiteQuantity = 0;
		int roomQuantity = 0;
		String hql = "FROM RoomRentBean WHERE roomRentMemId != " + wantedRoomBean.getWantedRoomAdMemId().getMemId();
		StringBuffer sbf = new StringBuffer(" AND adState = 1");
		sbf.append(" AND adAreacode IN (" + wantedRoomBean.getAreaCode() + ")");
		if(wantedRoomBean.getWantedRoommatesGender().equals("0")) {
			sbf.append(" AND adCurGender != 1");
		} else if(wantedRoomBean.getWantedRoommatesGender().equals("1")) {
			sbf.append(" AND adCurGender != 0");
		}
		if(wantedRoomBean.getJob().equals("0")) {
			sbf.append(" AND adFutureJobType != 1");
		} else if(wantedRoomBean.getJob().equals("1")) {
			sbf.append(" AND adFutureJobType != 0");
		} else if(wantedRoomBean.getJob().equals("2") || wantedRoomBean.getJob().equals("3")) {
			sbf.append(" AND adFutureJobType = 4");
		}
		if(wantedRoomBean.getWantedRoommatesSmoke() == true) {
			sbf.append(" AND (adCurSmoke != false OR adCurSmoke IS NULL)");
		} else if(wantedRoomBean.getWantedRoommatesSmoke() == false) {
			sbf.append(" AND (adCurSmoke != true OR adCurSmoke IS NULL)");
		}
		if(wantedRoomBean.getWantedRoommatesPet() == true) {
			sbf.append(" AND (adCurHasPet != false OR adCurHasPet IS NULL)");
		} else if(wantedRoomBean.getWantedRoommatesPet() == false) {
			sbf.append(" AND (adCurHasPet != true OR adCurHasPet IS NULL)");
		}
		if(wantedRoomBean.getAllowSmoke() == true) {
			sbf.append(" AND adFutureSmoke != false");
		}
		if(wantedRoomBean.getAllowPet() == true) {
			sbf.append(" AND adFuturePet != false");
		}
		
		hql += sbf;
		list = session.createQuery(hql).getResultList();
		
		Iterator<RoomRentBean> it = list.iterator();
		while(it.hasNext()){
			RoomRentBean roomRentBean = it.next();
			suiteQuantity = 0;
			roomQuantity = 0;
			for(RoomBean roomBean : roomRentBean.getRoomItems()) {
				if(roomBean.getRoomType().equals("0")) {
					suiteQuantity++;
				} else if(roomBean.getRoomType().equals("1")) {
					roomQuantity++;
				}
			}
			if((suiteQuantity < wantedRoomBean.getSuiteQuantity()) || (roomQuantity < wantedRoomBean.getRoomQuantity())) {
				it.remove();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WantedRoomBean> getAjaxSavedAdsByPage(MemberBean mb, Integer curPage, String sortOption) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("SELECT s.savedAdForWantedRoomAdFindRoomId FROM SavedAdForWantedRoomAdBean  s WHERE s.savedAdForWantedRoomAdMemId = :mb "
				+ "AND s.savedAdForWantedRoomAdFindRoomId.adState = true");
		
		if (sortOption.contentEquals("1")) {
			hqlBuffer.append(" ORDER BY s.savedAdForWantedRoomAdFindRoomId.budget ");
		}
		if (sortOption.contentEquals("2")) {
			hqlBuffer.append(" ORDER BY s.savedAdForWantedRoomAdFindRoomId.budget DESC");
		}
		if (sortOption.contentEquals("3")) {
			hqlBuffer.append(" ORDER BY s.savedAdForWantedRoomAdFindRoomId.adCreateDate DESC");
		}
		if (sortOption.contentEquals("4")) {
			hqlBuffer.append(" ORDER BY s.savedAdForWantedRoomAdFindRoomId.adUpdateDate DESC");
		}
		
		List<WantedRoomBean> list = null;

		list = session.createQuery(hqlBuffer.toString()).setParameter("mb", mb)
				.setFirstResult((curPage - 1) * 6).setMaxResults(6)
				.getResultList();

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getAjaxSavedAdsTotalPages(MemberBean mb, Integer curPage, String sortOption) {
		Session session = factory.getCurrentSession();
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("SELECT COUNT(*) FROM SavedAdForWantedRoomAdBean  s WHERE s.savedAdForWantedRoomAdMemId = :mb "
				+ "AND s.savedAdForWantedRoomAdFindRoomId.adState = true");
		
		List<Long> list = null;
		list = session.createQuery(hqlBuffer.toString()).setParameter("mb", mb)
				.getResultList();

		Integer totoalPages = (int) Math.ceil(list.get(0) / 6.0);
		return totoalPages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<List<Object>> getAjaxInterestedAdsByPage(MemberBean mb, Integer curPage) {
		Session session = factory.getCurrentSession();
		String hql = "FROM InterestedAdForWantedRoomAdBean  i WHERE i.interestedAdForWantedRoomAdFindRoomId.adState=true"
				+ " AND i.interestedAdForWantedRoomAdFindRoomId.wantedRoomAdMemId = :mb"
				+ " AND exists(FROM RoomRentBean r WHERE r.adState=true "
				+ " AND r.roomRentMemId = i.interestedAdForWantedRoomAdMemId)";
		String hql2 = "FROM RoomRentBean WHERE roomRentMemId = :mb AND adState=true ORDER BY adUpdateDate";
		List<List<Object>> objList= new ArrayList<List<Object>>();
		List<InterestedAdForWantedRoomAdBean> list = null;

		list = session.createQuery(hql).setParameter("mb", mb)
				.setFirstResult((curPage - 1) * 6).setMaxResults(6)
				.getResultList();
		
		if(list!=null && list.size()>0) {
			for(InterestedAdForWantedRoomAdBean bean : list) {
				List<Object> objListElement= new ArrayList<Object>();
				MemberBean memberBean = bean.getInterestedAdForWantedRoomAdMemId();
				RoomRentBean roomRentBean =  (RoomRentBean) session.createQuery(hql2)
											.setParameter("mb", memberBean).getResultList().get(0);
					objListElement.add(roomRentBean);
					objListElement.add(bean.getInterestedAdForWantedRoomAdFindRoomId().getAdTitle());
					objListElement.add(bean.getId());
					objList.add(objListElement);
				
			}
		}
		
		
		
		return objList;
	}

	@Override
	public Integer getAjaxInterestedAdsTotalPages(MemberBean mb, Integer curPage) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT count(*) FROM InterestedAdForWantedRoomAdBean  i WHERE i.interestedAdForWantedRoomAdFindRoomId.adState=true"
				+ " AND i.interestedAdForWantedRoomAdFindRoomId.wantedRoomAdMemId = :mb"
				+ " AND exists(FROM RoomRentBean r WHERE r.adState=true "
				+ " AND r.roomRentMemId = i.interestedAdForWantedRoomAdMemId)";
		Long count = null;
		count = (Long) session.createQuery(hql).setParameter("mb", mb)
				.getSingleResult();
	
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

	// 計算廣告總共有幾頁
	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	@Override
	 public int getMyAdTotalPagesByFk(MemberBean wantedRoomAdMemId) {
	  totalPages = (int) (Math.ceil(getMyAdRecordCountsByFk(wantedRoomAdMemId) / (double) recordsPerPage));
	  if(totalPages <= 0) {
	   totalPages = 1;
	  }
	  return totalPages;

	 }

	@Override
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	// 計算紀錄總筆數
	@Override
	public long getMyAdRecordCountsByFk(MemberBean wantedRoomAdMemId) {
		Long count = 0L; // 必須使用 long 型態
		String hql = "SELECT count(*) FROM WantedRoomBean WHERE wantedRoomAdMemId = :wantedRoomAdMemId";
		Session session = factory.getCurrentSession();
		count = (Long) session.createQuery(hql)
				.setParameter("wantedRoomAdMemId", wantedRoomAdMemId)
				.getSingleResult();
		return count;
	}

	@Override
	public int deleteFindRoomAdById(Integer id) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		WantedRoomBean bean = session.get(WantedRoomBean.class, id);		
		session.delete(bean);
		n++;
		return n;
	}

	@Override
	public int updateFindRoomAd(WantedRoomBean wantedRoomBean) {
		Integer n = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(wantedRoomBean);
		n++;
		return n;
	}

	@Override
	public int changeAdState(WantedRoomBean bean) {
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
	public List<WantedRoomBean> getPageWantedRoomAds() {
		List<WantedRoomBean> list = new ArrayList<>();
		String hql = "FROM WantedRoomBean";
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		list = session.createQuery(hql).
				setFirstResult(startRecordNo).
				setMaxResults(recordsPerPage).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WantedRoomBean> getAllWantedRoomAds() {
		Session session = factory.getCurrentSession();
		String hql = "FROM WantedRoomBean";
		List<WantedRoomBean> list = null;
		list = session.createQuery(hql).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WantedRoomBean> getPageAdByFk(int pageNo,MemberBean wantedRoomAdMemId) {
		Session session = factory.getCurrentSession();
		List<WantedRoomBean> list = null;
		String hql = "FROM WantedRoomBean WHERE wantedRoomAdMemId = :wantedRoomAdMemId";
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		list = session.createQuery(hql)
				.setParameter("wantedRoomAdMemId", wantedRoomAdMemId)
				.setFirstResult(startRecordNo)
				.setMaxResults(recordsPerPage)
				.getResultList();
		return list;
	}

}
