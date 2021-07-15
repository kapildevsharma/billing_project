package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Party")
public class Party  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8377080814308451337L;

	@Id
	@Column(name = "PK_PartyId", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int party_id;

	@Column(name = "Name", columnDefinition = "varchar(200)")
	private String name;

	@Column(name = "Contact_Number", columnDefinition = "varchar(50)")
	private String contact_no;

	@Column(name = "TIN_Number", columnDefinition = "varchar(100)")
	private String tin_number;

	@Column(name = "Address", columnDefinition = "text")
	private String address;

	@Column(name = "IsActive", columnDefinition = "bit default 0")
	private int isActive;

	public int getParty_id() {
		return party_id;
	}

	public void setParty_id(int party_id) {
		this.party_id = party_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getTin_number() {
		return tin_number;
	}

	public void setTin_number(String tin_number) {
		this.tin_number = tin_number;
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
