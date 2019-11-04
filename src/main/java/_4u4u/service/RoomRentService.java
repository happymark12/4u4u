package _4u4u.service;

import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface RoomRentService {
	List<RoomRentBean> getAllRoomRentAd();

	String getVIPAdsJsonData();

	int saveRoomRentAd(RoomRentBean roomRentBean); // 新增找房廣告

	boolean isExistEffectiveAd(MemberBean mb); // 判斷是否已經有有效的廣告

	RoomRentBean getAdById(Integer id);
	
	String getAjaxSearchJsonData(Map<String, String> conditionMap);
	
	public List<WantedRoomBean> findingMatchWantedRoomAd(RoomRentBean roomRentBean); // 找符合的WantedRoomAd

	String getAjaxSavedAdJsonData(MemberBean mb, Integer curPage,String sortOption);

	String getAjaxInterestedAdJsonData(MemberBean mb, Integer curPage);
	
	int changeAdState(RoomRentBean roomRentBean, MemberBean mb); // 改變廣告狀態

	int updateRoomRentAd(RoomRentBean roomRentBean); // 修改租房廣告

	int deleteRoomRentAd(Integer id);

	List<RoomRentBean> getPageAdByFk(int pageNo ,MemberBean roomRentMemId); // 依會員自己的id查詢自己的廣告 進行增刪改查

	int getMyAdTotalPagesByFk(MemberBean roomRentMemId); // 得到一頁的租房廣告 

}
