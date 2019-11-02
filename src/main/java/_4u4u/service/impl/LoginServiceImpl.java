package _4u4u.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.MemberBean;
import _4u4u.repository.MemberDao;
import _4u4u.service.LoginService;

// 登入時系統必須執行的checkIDPassword()功能交由 MemberDao來完成 
@Transactional
@Service
public class LoginServiceImpl implements LoginService {
	MemberDao  dao ;
	
	@Autowired
	public void setDao(MemberDao dao) {
		this.dao = dao;
	}


	public LoginServiceImpl() {
//		this.dao = new MemberDaoImpl();
	}

	
	public MemberBean checkEmailPassword(String userId, String password) {
		MemberBean mb = dao.checkEmailPassword(userId, password);
		return mb;
	}
}
