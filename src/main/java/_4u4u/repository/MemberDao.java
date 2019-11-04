package _4u4u.repository;

import java.util.List;

import _4u4u.model.MemberBean;

public interface MemberDao {
	
	public boolean emailExists(String email);

	public int saveMember(MemberBean mb) ;
	
	// 經由Session介面的load()查詢資料庫內的紀錄
//	public MemberBean loadMemberByEmail(String email); 
	public MemberBean loadMemberById(int id);
		
	// 經由Session介面的get()查詢資料庫內的紀錄
	public MemberBean getMemberByEmail(String email);
	public MemberBean getMemberById(int id);
	
	// 查詢此帳密是否正確
	public MemberBean checkEmailPassword(String email, String password);	
	
	public boolean changeMemberState(String email,String code);
	public void delete(int pk);

	public String memberState(MemberBean mb); // 查詢會員狀態

	public int saveLikeAd(MemberBean mb, String adStyle, Integer adId);

	public boolean cancelSavedAd(MemberBean mb, String adStyle, Integer adId);

	public String checkAdDetailButtonState(MemberBean mb, String adStyle, Integer adId);

	public int saveInterestedAd(MemberBean mb, String adStyle, Integer adId);

	public boolean cancelInterestedAd(MemberBean mb, String adStyle, Integer adId);

	public List<List<String>>  getPotentailBuddyUps(Integer adId);

	public List<List<String>> getPotentialWholePropertiesList(Integer memberId);

	public boolean deleteInterestedAdOnly(String adStyle, Integer adId);

	public int updateMember(MemberBean mb); //修改會員資料

}