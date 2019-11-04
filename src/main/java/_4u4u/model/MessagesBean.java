package _4u4u.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Messages")
public class MessagesBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer msgid;
	private Timestamp sendTime;
	private String detail;
	private Boolean isRead;
	private String title;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId_sendMemId")
	private MemberBean sendMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId_targetMemId")
	private MemberBean targetMemId;
	
	public MessagesBean() {
		super();
	}
	
	public MessagesBean(Timestamp sendTime, String detail,  MemberBean sendMemId,
			MemberBean targetMemId) {
		super();
		this.sendTime = sendTime;
		this.detail = detail;
		this.sendMemId = sendMemId;
		this.targetMemId = targetMemId;
	}

	public MessagesBean(Integer msgid, Timestamp sendTime, String detail,  MemberBean sendMemId,
			MemberBean targetMemId) {
		super();
		this.msgid = msgid;
		this.sendTime = sendTime;
		this.detail = detail;
		this.sendMemId = sendMemId;
		this.targetMemId = targetMemId;
	}
	
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public MemberBean getSendMemId() {
		return sendMemId;
	}
	public void setSendMemId(MemberBean sendMemId) {
		this.sendMemId = sendMemId;
	}
	public MemberBean getTargetMemId() {
		return targetMemId;
	}
	public void setTargetMemId(MemberBean targetMemId) {
		this.targetMemId = targetMemId;
	}

}


