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
 * The persistent class for the sim_buy database table.
 * 
 */
@Entity
@Table(name = "sim_buy")
public class SimBuy extends Auditable<User> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "buy_date")
	private Date buyDate;

	@Column(name = "cost")
	private Long cost;

	// bi-directional many-to-one association to Sim
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sim_id")
	private Sim sim;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer")
	private User buyer;

	public SimBuy() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Long getCost() {
		return this.cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}


	public Sim getSim() {
		return this.sim;
	}

	public void setSim(Sim sim) {
		this.sim = sim;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

}