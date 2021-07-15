package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Materials")
public class Materials  implements Serializable{
	    		  
	/**
	 * 
	 */
	private static final long serialVersionUID = 8028250453065578517L;

	@Id
	@Column(name="PK_MaterialId", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	@JoinColumn(name = "FK_PartyId")
	@OneToOne(cascade = CascadeType.ALL)
	private Party party;

	@NotNull
	@Column(name="MaterialName" ,columnDefinition = "varchar(200)")
	private String materialName;
	
	@NotNull
	@Column(name="MaterialType" ,columnDefinition = "varchar(50)")
	private String materialType;
	
	@NotNull
	@Column(name="Price",columnDefinition = "decimal(18,2)")
	private double price;

	@Column(name="Unit",columnDefinition = "varchar(200)")
	private double unit;
	
	@Column(name="IsActive", columnDefinition = "bit default 0")
	private int isActive;
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}