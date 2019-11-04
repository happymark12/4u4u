package _4u4u.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Authentication")
public class AuthenticationBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uUid;
	@OneToOne
	@JoinColumn(name = "fk_member_memId")
	private MemberBean authenticationMemId;

	public AuthenticationBean() {
		super();
	}
	public AuthenticationBean(String uUid, MemberBean authenticationMemId) {
		super();
		this.uUid = uUid;
		this.authenticationMemId = authenticationMemId;
	}
	public AuthenticationBean(Integer id, String uUid, MemberBean authenticationMemId) {
		super();
		this.id = id;
		this.uUid = uUid;
		this.authenticationMemId = authenticationMemId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getuUid() {
		return uUid;
	}
	public void setuUid(String uUid) {
		this.uUid = uUid;
	}
	public MemberBean getAuthenticationMemId() {
		return authenticationMemId;
	}
	public void setAuthenticationMemId(MemberBean authenticationMemId) {
		this.authenticationMemId = authenticationMemId;
	}
	
}
