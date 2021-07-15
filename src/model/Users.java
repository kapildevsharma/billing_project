package model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Users")
public class Users  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2137339077002515163L;

	@Id
	@Column(name="UserId", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(name="UserName" ,columnDefinition = "varchar(100) DEFAULT ''")
	private String username;
	
	@NotNull
	@Column(name="UserPassword",columnDefinition = "varchar(100)  DEFAULT ''")
	private String password;
	
	@Column(name="EmplyeeAddress", columnDefinition = "text")
	private String address;
	
	@Column(name="DateOfBirth", columnDefinition = "date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="ContactNumber", columnDefinition = "varchar(50)")
	private String contact_no;
	
	@Column(name="IsActive", columnDefinition = "bit default 0")
	private int isActive;
	
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

}
