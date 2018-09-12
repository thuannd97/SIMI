package com.linkin.simi.model;

public class InfoInstallmentDTO {
	
	private int id;
	
	private String dayPay;
	
	private double money;

	private String moneyReceiver;

	private int times;
	
	private Long idInstallment;
	
	public InfoInstallmentDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDayPay() {
		return dayPay;
	}

	public void setDayPay(String dayPay) {
		this.dayPay = dayPay;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getMoneyReceiver() {
		return moneyReceiver;
	}

	public void setMoneyReceiver(String moneyReceiver) {
		this.moneyReceiver = moneyReceiver;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Long getIdInstallment() {
		return idInstallment;
	}

	public void setIdInstallment(Long idInstallment) {
		this.idInstallment = idInstallment;
	}
	

}
