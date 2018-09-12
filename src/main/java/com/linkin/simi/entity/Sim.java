package com.linkin.simi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the sim database table.
 * 
 */
@Entity
@Table(name = "sim")
public class Sim extends TimeAuditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "price")
	private Long price;

	@Column(name = "sim_no")
	private String simNo;

	@Column(name = "status")
	private int status;

	// bi-directional many-to-one association to SimBuy
	@OneToMany(mappedBy = "sim", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SimBuy> simBuys;

	// bi-directional many-to-one association to SimSell
	@OneToMany(mappedBy = "sim", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SimSell> simSells;

	public Sim() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getSimNo() {
		return this.simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<SimBuy> getSimBuys() {
		return this.simBuys;
	}

	public void setSimBuys(List<SimBuy> simBuys) {
		this.simBuys = simBuys;
	}

	public SimBuy addSimBuy(SimBuy simBuy) {
		getSimBuys().add(simBuy);
		simBuy.setSim(this);

		return simBuy;
	}

	public SimBuy removeSimBuy(SimBuy simBuy) {
		getSimBuys().remove(simBuy);
		simBuy.setSim(null);

		return simBuy;
	}

	public List<SimSell> getSimSells() {
		return this.simSells;
	}

	public void setSimSells(List<SimSell> simSells) {
		this.simSells = simSells;
	}

	public SimSell addSimSell(SimSell simSell) {
		getSimSells().add(simSell);
		simSell.setSim(this);

		return simSell;
	}

	public SimSell removeSimSell(SimSell simSell) {
		getSimSells().remove(simSell);
		simSell.setSim(null);

		return simSell;
	}

}