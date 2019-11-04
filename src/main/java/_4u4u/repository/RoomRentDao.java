package _4u4u.repository;

import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface RoomRentDao {

	int saveRoomRentAd(RoomRentBean bean);

	boolean isExistEffectiveAd(MemberBean mb); // 判斷是否已經有有效的廣告

	RoomRentBean getAdById(Integer id);

	List<RoomRentBean> getAllRoomRentAd();

	List<RoomRentBean> getVIPOnlyAds();
	
	Integer getAjaxSearchResultTotalPages(Map<String, String> conditionMap);

	List<RoomRentBean> getAjaxSearchResultByPage(Map<String, String> conditionMap);
	
	public List<WantedRoomBean> findingMatchWantedRoomAd(RoomRentBean roomRentBean); // 找符合的WantedRoomAd

	List<RoomRentBean> getAjaxSavedAdsByPage(MemberBean mb, Integer curPage, String sortOption);

	Integer getAjaxSavedAdsTotalPages(MemberBean mb, String sortOption);

	List<List<Object>> getAjaxInterestedAdsByPage(MemberBean mb, Integer curPage);
	//list<Ojbect> 的 1st element wantedRoomAd 2nd element 對使用者感興趣的使用者租房廣告標題 
	//3rd element 是interestedAdPrimaryKey 

	Integer getAjaxInterestedAdsTotalPages(MemberBean mb);
	
	void setPageNo(int pageNo); // 存放目前顯示頁面的編號

	int getPageNo();

	void setRecordsPerPage(int recordsPerPage);

	int getRecordsPerPage();

	void setTotalPages(int totalPages);

	int getMyAdTotalPagesByFk(MemberBean roomRentMemId); // 得到總頁數

	long getMyAdRecordCountsByFk(MemberBean roomRentMemId); // 計算紀錄總筆數

	int deleteRoomRentAd(Integer id); // 刪除租房廣告

	int updateRoomRentAd(RoomRentBean bean); // 修改租房廣告

	int changeAdState(RoomRentBean bean); // 改變廣告狀態

	List<RoomRentBean> getPageRoomRentAds(); // 查詢某一頁的廣告資料

	List<RoomRentBean> getPageAdByFk(int pageNo, MemberBean roomRentMemId); // 用外鍵去查屬於自己的廣告
}