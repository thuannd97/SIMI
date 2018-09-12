package com.linkin.simi.model;

public class SearchCardBuyDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private String createdByName;
	private Integer cardType;
	private String buyDate;

	public SearchCardBuyDTO() {
		super();
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

}
