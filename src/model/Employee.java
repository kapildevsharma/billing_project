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
@Table(name="Employee")
public class Employee implements Serializable{

	private static final long serialVersionUID = 2137339077002515163L;
	
	@Id
	@Column(name="EmployeeId",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int emp_id;
	
	@NotNull
	@Column(name="EmployeeName",columnDefinition = "varchar(100) DEFAULT '' ")
	private String empname;
	
	@NotNull 
	@Column(name="EmployeePassword",columnDefinition = "varchar(100) DEFAULT '' ")
	private String emp_password;
	
	@NotNull
	@Column(name="Email",columnDefinition = "varchar(100) DEFAULT '' ")
	private String emailid;
	
	@Column(name="DateOfBirth", columnDefinition = "date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="DateOfJoining", columnDefinition = "date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	private Date doj;
	
	@Column(name="ContactNumber", columnDefinition = "varchar(50)")
	private String contact_no;
	
	@Column(name="EmployeeAddress", columnDefinition = "text")
	private String address;
	
	@Column(name="IsActive", columnDefinition = "bit default 0")
	private int isActive;

	
	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getEmp_password() {
		return emp_password;
	}

	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	
	
	
	
	
}
