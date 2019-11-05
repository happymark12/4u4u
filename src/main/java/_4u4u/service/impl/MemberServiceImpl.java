package _4u4u.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.MemberBean;
import _4u4u.repository.MemberDao;
import _4u4u.service.MemberService;
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

	MemberDao  dao ;
	
	@Autowired
	public void setDao(MemberDao dao) {
		this.dao = dao;
	}

	//	SessionFactory factory;
	public MemberServiceImpl() {
//		this.dao = new MemberDaoImpl();
//		this.factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public int saveMember(MemberBean mb) {
		int n= dao.saveMember(mb);
//		System.out.println(n);
		return n;		
	}

	@Override
	public boolean emailExists(String email) {
		boolean exist = false;
		exist = dao.emailExists(email);
		return exist;
	}	

	@Override
	public MemberBean queryMemberById(Integer id) {
		MemberBean mb= null;
		mb = dao.getMemberById(id);
		return mb;
	}

	@Override
	public MemberBean queryMemberByEmail(String email) {
		MemberBean mb= null;
		mb = dao.getMemberByEmail(email);
		return mb;	
	}

	@Override
	public MemberBean checkEmailPassword(String email, String password) {
		MemberBean mb = null;
		mb = dao.checkEmailPassword(email, password);
		return mb;
	}

	@Override
	public MemberBean queryAllMembers() {
		return null;
	}

	@Override
	public boolean changeMemberState(String email,String code) {
		return dao.changeMemberState(email,code);
	}
	
	@Override
	public String memberState(MemberBean mb) {
		return dao.memberState(mb);
	}

	@Override
	public int saveLikeAd(MemberBean mb, String adStyle, Integer adId) {
		return dao.saveLikeAd(mb,adStyle,adId);
	}

	@Override
	public boolean cancelSavedAd(MemberBean mb, String adStyle, Integer adId) {
		return dao.cancelSavedAd(mb,adStyle,adId);
	}

	@Override
	public String checkAdDetailButtonState(MemberBean mb, String adStyle, Integer adId) {
		
		return dao.checkAdDetailButtonState( mb,  adStyle,  adId);
	}

	@Override
	public int saveInterestedAd(MemberBean mb, String adStyle, Integer adId) {
		return dao.saveInterestedAd(mb,adStyle,adId);
	}

	@Override
	public boolean cancelInterestedAd(MemberBean mb, String adStyle, Integer adId) {
		return dao.cancelInterestedAd(mb,adStyle,adId);
	}

	@Override
	public List<List<String>> getPotentialBuddyUps(Integer adId) {
		return dao.getPotentailBuddyUps(adId);
	}

	@Override
	public List<List<String>> getPotentialWholePropertiesList(Integer memberId) {
		return dao.getPotentialWholePropertiesList(memberId);
	}

	@Override
	public boolean deleteInterestedAdOnly( String adStyle, Integer adId) {
		return dao.deleteInterestedAdOnly(adStyle,adId);
	}
	
	@Override
	public int updateMember(MemberBean mb) {
		return dao.updateMember(mb);
	}

	@Override
	public boolean checkAdOwner(MemberBean mb, String adStyle, Integer adId) {
		return dao.checkAdOwner(mb,adStyle,adId);
	}

}
