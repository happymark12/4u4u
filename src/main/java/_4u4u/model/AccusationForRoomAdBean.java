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
@Table(name = "AccusationForRoomAd")
public class AccusationForRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accusationId;
	private String accusationContents;
	private Timestamp accusationTime;
	private Boolean hashandling;
	private String handlingContents;
	private Timestamp handlingTime;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_member_memId")
	private MemberBean accusationForRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_roomAd_adId")
	private RoomRentBean accusationForRoomAdAdId;
	
	public AccusationForRoomAdBean() {
		super();
	}
	public AccusationForRoomAdBean(String accusationContents, Timestamp accusationTime, Boolean hashandling,
			String handlingContents, Timestamp handlingTime, MemberBean accusationForRoomAdMemId,
			RoomRentBean accusationForRoomAdAdId) {
		super();
		this.accusationContents = accusationContents;
		this.accusationTime = accusationTime;
		this.hashandling = hashandling;
		this.handlingContents = handlingContents;
		this.handlingTime = handlingTime;
		this.accusationForRoomAdMemId = accusationForRoomAdMemId;
		this.accusationForRoomAdAdId = accusationForRoomAdAdId;
	}
	public AccusationForRoomAdBean(Integer accusationId, String accusationContents, Timestamp accusationTime,
			Boolean hashandling, String handlingContents, Timestamp handlingTime, MemberBean accusationForRoomAdMemId,
			RoomRentBean accusationForRoomAdAdId) {
		super();
		this.accusationId = accusationId;
		this.accusationContents = accusationContents;
		this.accusationTime = accusationTime;
		this.hashandling = hashandling;
		this.handlingContents = handlingContents;
		this.handlingTime = handlingTime;
		this.accusationForRoomAdMemId = accusationForRoomAdMemId;
		this.accusationForRoomAdAdId = accusationForRoomAdAdId;
	}
	
	public Integer getAccusationId() {
		return accusationId;
	}
	public void setAccusationId(Integer accusationId) {
		this.accusationId = accusationId;
	}
	public String getAccusationContents() {
		return accusationContents;
	}
	public void setAccusationContents(String accusationContents) {
		this.accusationContents = accusationContents;
	}
	public Timestamp getAccusationTime() {
		return accusationTime;
	}
	public void setAccusationTime(Timestamp accusationTime) {
		this.accusationTime = accusationTime;
	}
	public Boolean getHashandling() {
		return hashandling;
	}
	public void setHashandling(Boolean hashandling) {
		this.hashandling = hashandling;
	}
	public String getHandlingContents() {
		return handlingContents;
	}
	public void setHandlingContents(String handlingContents) {
		this.handlingContents = handlingContents;
	}
	public Timestamp getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(Timestamp handlingTime) {
		this.handlingTime = handlingTime;
	}
	public MemberBean getAccusationForRoomAdMemId() {
		return accusationForRoomAdMemId;
	}
	public void setAccusationForRoomAdMemId(MemberBean accusationForRoomAdMemId) {
		this.accusationForRoomAdMemId = accusationForRoomAdMemId;
	}
	public RoomRentBean getAccusationForRoomAdAdId() {
		return accusationForRoomAdAdId;
	}
	public void setAccusationForRoomAdAdId(RoomRentBean accusationForRoomAdAdId) {
		this.accusationForRoomAdAdId = accusationForRoomAdAdId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
