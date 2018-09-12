package com.linkin.simi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the sim_sell database table.
 * 
 */
@Entity
@Table(name = "sim_sell")
public class SimSell extends Auditable<User> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "customer_info")
	private String customerInfo;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "deposit")
	private Long deposit;

	@Temporal(TemporalType.DATE)
	@Column(name = "sell_date")
	private Date sellDate;

	@Column(name = "sell_price")
	private Long sellPrice;

	// bi-directional many-to-one association to Sim
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sim_id")
	private Sim sim;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller")
	private User seller;

	public SimSell() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerInfo() {
		return this.customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public Date getSellDate() {
		return this.sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public Long getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Sim getSim() {
		return this.sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof SimSell ? ((SimSell) obj).getId() == this.id : false;
	}
}