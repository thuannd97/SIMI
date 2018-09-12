package com.linkin.simi.model;

public class SearchSimSellDTO extends SearchDTO {
	private static final long serialVersionUID = 1L;

	private String sellerName;
	private String createdName;
	private String fromDate;
	private String toDate;

	public SearchSimSellDTO() {
		super();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

}
