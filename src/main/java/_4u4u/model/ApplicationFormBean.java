package _4u4u.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ApplicationForm", uniqueConstraints = {@UniqueConstraint(columnNames= {"email","fk_event_eventId"})})
public class ApplicationFormBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String phone;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_event_eventId")
	private EventBean applicationFormEventId;

	public ApplicationFormBean() {
	}
	public ApplicationFormBean(String name, String email, EventBean applicationFormEventId,String phone) {
		this.name = name;
		this.email = email;
		this.applicationFormEventId = applicationFormEventId;
	}
	public ApplicationFormBean(Integer id, String name, String email, EventBean applicationFormEventId,String phone) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.applicationFormEventId = applicationFormEventId;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public EventBean getApplicationFormEventId() {
		return applicationFormEventId;
	}
	public void setApplicationFormEventId(EventBean applicationFormEventId) {
		this.applicationFormEventId = applicationFormEventId;
	}
	
}
