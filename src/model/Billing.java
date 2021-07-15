package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="Billing")
public class Billing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4808759425563720523L;

	@Id
	@Column(name="BillingId", nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int billingId;
	
	@NotNull
	@ManyToOne
    @JoinColumn(name = "FK_MaterialId")
	private Materials material;
	
	@NotNull
	@JoinColumn(name = "FK_PartyId")
	@OneToOne(cascade = CascadeType.ALL)
	private Party party;
	
	
	@NotNull
	@Column(name="PaymentType" ,columnDefinition = "varchar(100)")
	private String paymentType;
	
	@NotNull
	@Column(name="Vat",columnDefinition = "decimal(18,2)")
	private double vat;
	
	@Column(name="OtherCharges",columnDefinition = "decimal(18,2)")
	private double otherCharges;
	
	@Column(name="Discount",columnDefinition = "decimal(18,2)")
	private double discount;
	
	@Column(name="CreatedBy",columnDefinition = "decimal(18,2)")
	private int createdBy;
	
	@Column(name="IsActive", columnDefinition = "bit default 0")
	private int isActive;
	
	public int getBillingId() {
		return billingId;
	}

	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Materials getMaterial() {
		return material;
	}

	public void setMaterial(Materials material) {
		this.material = material;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	

}
