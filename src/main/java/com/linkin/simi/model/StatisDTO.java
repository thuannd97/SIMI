package com.linkin.simi.model;

import java.io.Serializable;

public class StatisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quantitySimBuy;
	private Long quantitySimSell;
	private Long priceTotal;
	private Long costTotal;
	private Long deposit;

	public StatisDTO() {
		super();
	}

	public Long getQuantitySimBuy() {
		return quantitySimBuy;
	}

	public void setQuantitySimBuy(Long quantitySimBuy) {
		this.quantitySimBuy = quantitySimBuy;
	}

	public Long getQuantitySimSell() {
		return quantitySimSell;
	}

	public void setQuantitySimSell(Long quantitySimSell) {
		this.quantitySimSell = quantitySimSell;
	}

	public Long getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Long priceTotal) {
		this.priceTotal = priceTotal;
	}

	public Long getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(Long costTotal) {
		this.costTotal = costTotal;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

}
