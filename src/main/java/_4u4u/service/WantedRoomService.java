package _4u4u.service;

import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface WantedRoomService {
//	List<WantedRoomBean> selectAll();

//	WantedRoomBean getWantedRoomAdById(int adId);// =依adId搜尋租屋需求廣告

	int saveFindRoomAd(WantedRoomBean wantedRoomBean); // =新增找房廣告

	boolean isExistAd(MemberBean mb); // 判斷是否已經有廣告
	boolean isExistEffectiveAd(MemberBean mb); //判斷是否有有效廣告
	WantedRoomBean getAdById(Integer id);

//	int updateFindRoomAd(WantedRoomBean wantedRoomBean); // =修改找房廣告
	
//	int deleteFindRoomAd(int adId); // =刪除找房廣告

	String getAjaxSearchJsonData(Map<String, String> conditionMap);

	public List<RoomRentBean> findingMatchRoomAd(WantedRoomBean wantedRoomBean); // 找符合的RoomAd

	String getAjaxSavedAdJsonData(MemberBean mb, Integer curPage,String sortOption);

	String getAjaxInterestedAdJsonData(MemberBean mb, Integer curPage);
	int updateFindRoomAd(WantedRoomBean wantedRoomBean); // =修改找房廣告

	int deleteFindRoomAd(int adId); // =刪除找房廣告

	int changeAdState(WantedRoomBean wantedRoomBean); // 更改廣告狀態

	List<WantedRoomBean> getAllWantedRoomAds(); // 查詢所有找房廣告

	List<WantedRoomBean> getPageAdByFk(int pageNo ,MemberBean wantedRoomAdMemId); // 依外鍵來查詢會員自己的廣告

	int getMyAdTotalPagesByFk(MemberBean wantedRoomAdMemId); // 得到一頁的找房廣告

	String  getVIPAdsJsonData();
}
