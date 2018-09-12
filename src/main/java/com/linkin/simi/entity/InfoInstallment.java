package com.linkin.simi.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the info_installment database table.
 * 
 */
@Entity
@Table(name="info_installment")
public class InfoInstallment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="day_pay")
	private Date dayPay;
	
	@Column(name="money")
	private double money;

	@Column(name="money_receiver")
	private String moneyReceiver;

	@Column(name="times")
	private int times;

	//bi-directional many-to-one association to Installment
	@ManyToOne
	@JoinColumn(name="id_installment")
	private Installment installment;

	public InfoInstallment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDayPay() {
		return this.dayPay;
	}

	public void setDayPay(Date dayPay) {
		this.dayPay = dayPay;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getMoneyReceiver() {
		return this.moneyReceiver;
	}

	public void setMoneyReceiver(String moneyReceiver) {
		this.moneyReceiver = moneyReceiver;
	}

	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Installment getInstallment() {
		return this.installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

}