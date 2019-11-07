package _4u4u.service;

import java.util.List;

import _4u4u.model.MemberBean;

public interface MemberService {
	
	public boolean emailExists(String id);
	public int saveMember(MemberBean mb);
	public MemberBean queryMemberById(Integer id);
	public MemberBean queryMemberByEmail(String email);
	public MemberBean queryAllMembers();
	public MemberBean checkEmailPassword(String userId, String password);
	public boolean changeMemberState(String email,String code);
	public String memberState(MemberBean mb); // 查詢會員狀態
	public int saveLikeAd(MemberBean mb, String adStyle, Integer adId); //儲存廣告
	public boolean cancelSavedAd(MemberBean mb, String adStyle, Integer adId); //取消儲存廣告
	public String checkAdDetailButtonState(MemberBean mb, String adStyle, Integer adId);
	public int saveInterestedAd(MemberBean mb, String adStyle, Integer adId);
	public boolean cancelInterestedAd(MemberBean mb, String adStyle, Integer adId);
	public List<List<String>>  getPotentialBuddyUps(Integer adId); //list中 每個元素 有3個  1st memberId 2nd memberName 3rd wantedAdId   
	public List<List<String>> getPotentialWholePropertiesList(Integer memberId); //list中 每個元素 有3個  1st roomRentAdId 2nd imageFileName 3rd roomRentAdTitle   
	public boolean deleteInterestedAdOnly(String adStyle, Integer adId);
	public int updateMember(MemberBean mb); //修改會員資料
	public String checkAdContactStatus(MemberBean mb,String adStyle, Integer adId);
	
}
