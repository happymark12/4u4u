package _4u4u.service;

import _4u4u.model.MemberBean;
// 定義進行登入時系統必須執行的功能
public interface LoginService {
	public MemberBean checkEmailPassword(String userId, String password) ;
}
