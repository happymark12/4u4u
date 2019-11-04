package _4u4u.repository;

import java.util.List;
import java.util.Map;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface WantedRoomDao {

	int saveFindRoomAd(WantedRoomBean wantedRoomBean); // 新增找房廣告

	boolean isExistAd(MemberBean mb); // 判斷是否已經有廣告
	boolean isExistEffectiveAd(MemberBean mb);
	WantedRoomBean getAdById(Integer id);

	List<WantedRoomBean> getAjaxSearchResultByPage(Map<String, String> conditionMap);
	
	Integer getAjaxSearchResultTotalPages(Map<String, String> conditionMap);

	public List<RoomRentBean> findingMatchRoomAd(WantedRoomBean wantedRoomBean); // 找符合的RoomAd

	List<WantedRoomBean> getAjaxSavedAdsByPage(MemberBean mb, Integer curPage, String sortOption);

	Integer getAjaxSavedAdsTotalPages(MemberBean mb, Integer curPage, String sortOption);

	List<List<Object>> getAjaxInterestedAdsByPage(MemberBean mb, Integer curPage);
	//list<Ojbect> 的 1st element roomRentAd 2nd element 對使用者感興趣的使用者租房廣告標題 
		//3rd element 是interestedAdPrimaryKey 
	Integer getAjaxInterestedAdsTotalPages(MemberBean mb, Integer curPage);

	void setPageNo(int pageNo); // 存放目前顯示頁面的編號

	int getPageNo();

	void setRecordsPerPage(int recordsPerPage);

	int getRecordsPerPage();

	void setTotalPages(int totalPages);

	int getMyAdTotalPagesByFk(MemberBean wantedRoomAdMemId); // 得到我的廣告總頁數

	long getMyAdRecordCountsByFk(MemberBean wantedRoomAdMemId); // 計算得到我的廣告紀錄總筆數

	int deleteFindRoomAdById(Integer id); // 刪除找房廣告

	int updateFindRoomAd(WantedRoomBean wantedRoomBean); // 更新找房廣告

	int changeAdState(WantedRoomBean wantedRoomBean); // 更改廣告狀態(有效=>失效)

	List<WantedRoomBean> getPageWantedRoomAds(); // 查詢某一頁的廣告資料

	List<WantedRoomBean> getAllWantedRoomAds(); // 查詢所有的找房廣告

	List<WantedRoomBean> getPageAdByFk(int pageNo , MemberBean wantedRoomAdMemId); // 依fk(會員自己id)查詢自己所有發的廣告
}
