package _4u4u.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import _4u4u.model.FinalJsonObject;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.repository.RoomRentDao;
import _4u4u.service.RoomRentService;
import _4u4u_init.util.ConvertTableUtil;
@Transactional
@Service
public class RoomRentServiceImpl implements Serializable, RoomRentService {
	private static final long serialVersionUID = 1L;
	RoomRentDao roomRentdao;
	
	@Autowired
	public void setRoomRentdao(RoomRentDao roomRentdao) {
		this.roomRentdao = roomRentdao;
	}

	public RoomRentServiceImpl() {
//		this.roomRentdao = new RoomRentDaoImpl();
	}
	@Override
	public List<RoomRentBean> getAllRoomRentAd() {

		return roomRentdao.getAllRoomRentAd();
	}
	
	@Override
	public RoomRentBean getAdById(Integer id) {
		RoomRentBean bean = roomRentdao.getAdById(id);
		return bean;
	}

	@Override
	public int saveRoomRentAd(RoomRentBean roomRentBean) {
		return roomRentdao.saveRoomRentAd(roomRentBean);
	}
	
	@Override
	public boolean isExistEffectiveAd(MemberBean mb) {
		return roomRentdao.isExistEffectiveAd(mb);
	}

	@Override
	public String getAjaxSearchJsonData(Map<String, String> conditionMap) {
		List<RoomRentBean>	list =roomRentdao.getAjaxSearchResultByPage(conditionMap);
		Integer totalPages = roomRentdao.getAjaxSearchResultTotalPages(conditionMap);
//		System.out.println("totalPages"+totalPages);
		return getRoomRentBeanJson(list, conditionMap.get("sortOption"),totalPages);
	}
	//隨機傳回6則VIP廣告 (JSON格式)
	@Override
	public String getVIPAdsJsonData() {
		List<RoomRentBean> resultList =  roomRentdao.getVIPOnlyAds();
		Set<RoomRentBean> list = new LinkedHashSet<RoomRentBean>();
		int totalNum  = resultList.size();
		if(totalNum<=6) {
			list.addAll(resultList);
		}else {
			while(list.size()<6) {
				int n = (int)(Math.random()*totalNum);
				list.add(resultList.get(n));
			}
			
		}
		
		return getRoomRentBeanJson(list, "0",1);
	}

	
	@Override
	public List<WantedRoomBean> findingMatchWantedRoomAd(RoomRentBean roomRentBean) {
		return roomRentdao.findingMatchWantedRoomAd(roomRentBean);
	}

	@Override
	public String getAjaxSavedAdJsonData(MemberBean mb, Integer curPage,String sortOption) {
		List<RoomRentBean>	list =roomRentdao.getAjaxSavedAdsByPage(mb,curPage,sortOption);
		Integer totalPages = roomRentdao.getAjaxSavedAdsTotalPages(mb,sortOption);
		return getRoomRentBeanJson(list, sortOption,totalPages);
	}
	@Override
	public String getAjaxInterestedAdJsonData(MemberBean mb, Integer curPage) {
		List<List<Object>>	list =roomRentdao.getAjaxInterestedAdsByPage(mb,curPage);
		
		Integer totalPages = roomRentdao.getAjaxInterestedAdsTotalPages(mb);
		return getWantdRoomBeanJson(list, "0",totalPages);
	}

	private String getRoomRentBeanJson(Collection<RoomRentBean> list, String sortOption,Integer totalPages) {
		List<FinalJsonObject> normalList = new ArrayList<FinalJsonObject>();
		List<FinalJsonObject> vipList = new ArrayList<FinalJsonObject>();
		List<FinalJsonObject> resultList = new ArrayList<FinalJsonObject>();
		// for rentPrice 排序
		Map<Integer, FinalJsonObject> sortMap = new HashMap<Integer, FinalJsonObject>();
		List<List<Object>> sortList = new ArrayList<List<Object>>(); //1st element rentPrice 2nd element FinalJsonObject
		if (list.size() == 0) {
			;
		} else {

			try {
				for (RoomRentBean bean : list) {
					if (!bean.getRoomRentMemId().getState().contentEquals("1")
							&& !bean.getRoomRentMemId().getState().contentEquals("2")) {
						continue;
					}

					if (!bean.getAdState()) {
						continue;
					}
					FinalJsonObject finalBean = new FinalJsonObject();
					Iterator<RoomBean> itr = bean.getRoomItems().iterator();
					List<Integer> rentPriceList = new ArrayList<Integer>();
					Integer suiteQuantity = 0;
					Integer nonSuiteQuantity = 0;
					while (itr.hasNext()) {
						RoomBean room = itr.next();
						rentPriceList.add(room.getRentPrice());
						if (room.getRoomType().equals("0")) {
							suiteQuantity++;
						} else {
							nonSuiteQuantity++;
						}
					}
					Collections.sort(rentPriceList);
					// for RoomRentAd
					finalBean.setTotalPages(totalPages);
					finalBean.setAdId(bean.getAdId());
					finalBean.setAdTitle(bean.getAdTitle());
					finalBean.setAdStyle(bean.getAdStyle());
					// images
					if (bean.getAdImages() != null && bean.getAdImages().trim().length() != 0) {
						finalBean.setAdImages(bean.getAdImages().split(","));
					} else {
						finalBean.setAdImages(new String[] {});
					}
					//contactState
					Date createDate = new Date(bean.getAdCreateDate().getTime());
					Date now = new Date();
					long diffDays = (now.getTime()-createDate.getTime()) / (24 * 60 * 60 * 1000);
					if(diffDays<7 && bean.getRoomRentMemId().getState().contentEquals("1")) {
						finalBean.setContactState("1");
					}else if(diffDays>=7 && bean.getRoomRentMemId().getState().contentEquals("1")) {
						finalBean.setContactState("2");
					}else {
						finalBean.setContactState("2");
					}
					// RentPrice
					if (bean.getAdRentType().contentEquals("0")) {
						finalBean.setAdRentPrice("$" + bean.getAdRentPrice());
					} else {
						if (rentPriceList.size() > 1) {
							if (rentPriceList.get(0).equals(rentPriceList.get(rentPriceList.size() - 1))) {
								finalBean.setAdRentPrice("$" + rentPriceList.get(0));
							} else {
								finalBean.setAdRentPrice("$" + rentPriceList.get(0) + "-" + "$"
										+ rentPriceList.get(rentPriceList.size() - 1));
							}
						} else {
							finalBean.setAdRentPrice("$" + rentPriceList.get(0));
						}
					}
					// address
					finalBean.setAdaddress(ConvertTableUtil.addressCodeToString(bean.getAdAreacode()));
					// renttype
					finalBean.setAdRentType(ConvertTableUtil.rentCodeToString(bean.getAdRentType()));
					// roomType
					if (bean.getAdRentType().contentEquals("0")) {
						String livingRoom = bean.getAdLivingRoomNum();
						String toilet = bean.getAdToiletNum();
						String balcony = bean.getAdBalconyNum();
						String roomType = "";
						if (livingRoom.contentEquals("0")) {

							if (toilet.contentEquals("0")) {

								if (!balcony.contentEquals("0")) {
									roomType = balcony + "陽台";
								}

							} else {
								if (balcony.contentEquals("0")) {
									roomType = toilet + "衛";
								} else {
									roomType = toilet + "衛" + balcony + "陽台";
								}

							}

						} else {
							if (toilet.contentEquals("0")) {

								if (!balcony.contentEquals("0")) {
									roomType = livingRoom + "廳" + balcony + "陽台";
								} else {
									roomType = livingRoom + "廳";
								}

							} else {
								if (balcony.contentEquals("0")) {
									roomType = livingRoom + "廳" + toilet + "衛";
								} else {
									roomType = livingRoom + "廳" + toilet + "衛" + balcony + "陽台";
								}

							}

						}

						finalBean.setRoomType(bean.getAdRoomNum() + "房" + roomType);
					} else {
						if (suiteQuantity == 0) {
							finalBean.setRoomType(nonSuiteQuantity + "雅房");
						} else {
							if (nonSuiteQuantity != 0) {
								finalBean.setRoomType(suiteQuantity + "套房" + nonSuiteQuantity + "雅房");
							} else {
								finalBean.setRoomType(suiteQuantity + "套房");
							}

						}

					}
					if (bean.getRoomRentMemId().getState().contentEquals("1")) {
						finalBean.setMemberState("1");
					}else {
						finalBean.setMemberState("2");
					}
					
					
					if (sortOption != null) {
						if (sortOption.contentEquals("1") || sortOption.contentEquals("2")) {
							sortMap.put(rentPriceList.get(rentPriceList.size() - 1), finalBean);
							
						}
						if (sortOption.contentEquals("3") || sortOption.contentEquals("4")) {
							resultList.add(finalBean);
						}
					}

					if (bean.getRoomRentMemId().getState().contentEquals("1")) {

						normalList.add(finalBean);
					} else {
						vipList.add(finalBean);
					}
				} // for loop end
				if (sortOption != null) {
					if (sortOption.contentEquals("1")) {

						ArrayList<Integer> keysList = new ArrayList<Integer>(sortMap.keySet());
						Collections.sort(keysList);

						for (Integer i : keysList) {
							resultList.add(sortMap.get(i));
						}

					} else if (sortOption.contentEquals("2")) {
						ArrayList<Integer> keysList = new ArrayList<Integer>(sortMap.keySet());
						Collections.sort(keysList);
						Collections.reverse(keysList);
						for (Integer i : keysList) {
							resultList.add(sortMap.get(i));
						}
					} else if (sortOption.contentEquals("0")) {
					
						for(FinalJsonObject vipObj:vipList) {
							resultList.add(vipObj);
						}
						for(FinalJsonObject normalObj:normalList) {
							resultList.add(normalObj);
						}
							
					}
				}else {
					for(FinalJsonObject vipObj:vipList) {
						resultList.add(vipObj);
					}
					for(FinalJsonObject normalObj:normalList) {
						resultList.add(normalObj);
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // else bracket end
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(resultList);
//		System.out.println(jsonStr);
		return jsonStr;
	}

	


	private String getWantdRoomBeanJson(List<List<Object>> list, String sortOption,Integer totalPages) {
		List<FinalJsonObject> normalList = new ArrayList<FinalJsonObject>();
		List<FinalJsonObject> vipList = new ArrayList<FinalJsonObject>();
		List<FinalJsonObject> resultList = new ArrayList<FinalJsonObject>();
		if(list!=null) {
		if (list.size() == 0) {
			;
		} else {

			try {
				for (List<Object> beanList : list) {
					WantedRoomBean bean = (WantedRoomBean)beanList.get(0);
					if (!bean.getWantedRoomAdMemId().getState().contentEquals("1")
							&& !bean.getWantedRoomAdMemId().getState().contentEquals("2")) {
						continue;
					}

					if (!bean.getAdState()) {
						continue;
					}
					FinalJsonObject finalBean = new FinalJsonObject();
					finalBean.setInterestedAdTitle((String)beanList.get(1));
					finalBean.setInterestedPrimaryKey((Integer)beanList.get(2));
					finalBean.setTotalPages(totalPages);
					finalBean.setAdId(bean.getFindRoomId());
					finalBean.setAdTitle(bean.getAdTitle());
					finalBean.setAdStyle(bean.getAdStyle());
					// images
					if (bean.getImages() != null && bean.getImages().trim().length() != 0) {
						finalBean.setAdImages(bean.getImages().split(","));
					} else {
						finalBean.setAdImages(new String[] {});
					}
					//memberState
					if (bean.getWantedRoomAdMemId().getState().contentEquals("1")) {
						finalBean.setMemberState("1");
					}else {
						finalBean.setMemberState("2");
					}
					
					//contactState
					Date createDate = new Date(bean.getAdCreateDate().getTime());
					Date now = new Date();
					long diffDays = (now.getTime()-createDate.getTime()) / (24 * 60 * 60 * 1000);
					if(diffDays<7 && bean.getWantedRoomAdMemId().getState().contentEquals("1")) {
						finalBean.setContactState("1");
					}else if(diffDays>=7 && bean.getWantedRoomAdMemId().getState().contentEquals("1")) {
						finalBean.setContactState("2");
					}else {
						finalBean.setContactState("2");
					}
					// Budget
					finalBean.setBudget("總預算 : $" + bean.getBudget());
					// peopleNumGender
					finalBean.setPeopleNumGender(bean.getPeopleNumGender());
					// wantedRoomType
					if (bean.getSuiteQuantity() == 0) {
						finalBean.setWantedRoomType(bean.getRoomQuantity() + "雅房");
					} else {
						if (bean.getRoomQuantity() != 0) {
							finalBean.setWantedRoomType(bean.getSuiteQuantity() + "套房" + bean.getRoomQuantity() + "雅房");
						} else {
							finalBean.setWantedRoomType(bean.getSuiteQuantity() + "套房");
						}

					}
					// age
					if (bean.getAge() != null && bean.getAge().trim().length() != 0) {
						String[] ageArray = bean.getAge().trim().split(",");
						if (ageArray.length == 2) {
							if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
								;
							} else {
								String age = bean.getAge().trim().replace(",", " - ");
								finalBean.setAge(age);
							}
						} else {
							finalBean.setAge(bean.getAge().trim());
						}

					}
					// job
					finalBean.setJob(ConvertTableUtil.jobCodeToString(bean.getJob()));
					// name

					finalBean.setName(bean.getWantedRoomAdMemId().getName());
					if (sortOption != null && !sortOption.contentEquals("0")) {
						resultList.add(finalBean);
					}
					if (bean.getWantedRoomAdMemId().getState().contentEquals("1")) {

						normalList.add(finalBean);
					} else {
						vipList.add(finalBean);
					}
				} // for loop end
				if (sortOption != null && sortOption.contentEquals("0")) {
					for(FinalJsonObject vipObj:vipList) {
						resultList.add(vipObj);
					}
					for(FinalJsonObject normalObj:normalList) {
						resultList.add(normalObj);
					}				
				}else if(sortOption == null) {
					for(FinalJsonObject vipObj:vipList) {
						resultList.add(vipObj);
					}
					for(FinalJsonObject normalObj:normalList) {
						resultList.add(normalObj);
					}		
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // else bracket end
		}
		Gson gson = new Gson();
		String jsonStr = gson.toJson(resultList);
		return jsonStr;
	}

	
	@Override
	public int changeAdState(RoomRentBean roomRentBean, MemberBean mb) {
		int n = 0;
//		判斷會員狀態(一般會員)
		if (mb.getState().equals("1")) {
//			 廣告是失效的話(失效(false)轉有效)
			if (roomRentBean.getAdState() == false) {
//				判斷是否已有[有效廣告]
				if (roomRentdao.isExistEffectiveAd(mb) == false) {
					n = roomRentdao.changeAdState(roomRentBean);
				} else {
					throw new RuntimeException("已經有1則或是1則以上的租房廣告存在");
				}
			} else {// 有效轉失效
				n = roomRentdao.changeAdState(roomRentBean);
			}
		} else if (mb.getState().equals("2")) {
			n = roomRentdao.changeAdState(roomRentBean);
		} else if (mb.getState().equals("0") || mb.getState().equals("3") || mb.getState().equals("4")) {
			throw new RuntimeException("此帳號已被凍結或未激活");
		}
		return n;
	}
	
	@Override
	public int updateRoomRentAd(RoomRentBean roomRentBean) {		
		return roomRentdao.updateRoomRentAd(roomRentBean);
	}

	@Override
	public int deleteRoomRentAd(Integer id) {
		return roomRentdao.deleteRoomRentAd(id);
	}

	@Override
	public List<RoomRentBean> getPageAdByFk(int pageNo , MemberBean roomRentMemId) {
		return roomRentdao.getPageAdByFk(pageNo, roomRentMemId);
	}

	@Override
	public int getMyAdTotalPagesByFk(MemberBean roomRentMemId) {		
		return roomRentdao.getMyAdTotalPagesByFk(roomRentMemId);
	}

	

	
	

	
	
	
}
