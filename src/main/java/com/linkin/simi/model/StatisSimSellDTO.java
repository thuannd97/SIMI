package com.linkin.simi.model;

import java.io.Serializable;

public class StatisSimSellDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quantity;
	private Long totalMoney;
	private Long deposite;
	
	public StatisSimSellDTO() {
		super();
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Long getDeposite() {
		return deposite;
	}

	public void setDeposite(Long deposite) {
		this.deposite = deposite;
	}

}
