package com.linkin.simi.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the installment database table.
 * 
 */
@Entity
@Table(name="installment")
public class Installment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="customer_phone")
	private String customerPhone;

	@Temporal(TemporalType.DATE)
	@Column(name="day_start")
	private Date dayStart;

	@Column(name="interest")
	private double interest;

	@Column(name="loan")
	private double loan;

	@Column(name="sim_No")
	private String simNo;
	
	@Column(name="status")
	private int status;

	//bi-directional many-to-one association to PayType
	@ManyToOne
	@JoinColumn(name="id_pay_type")
	private PayType payType;

	public Installment() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Date getDayStart() {
		return this.dayStart;
	}

	public void setDayStart(Date dayStart) {
		this.dayStart = dayStart;
	}

	public double getInterest() {
		return this.interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getLoan() {
		return this.loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}

	public String getSimNo() {
		return this.simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public PayType getPayType() {
		return this.payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}