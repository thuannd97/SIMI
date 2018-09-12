package com.linkin.simi.model;

public class SearchCardSellDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private Integer cardType;
	private Long denomination;
	private String sellDate;
	private String createdByName;

	public SearchCardSellDTO() {
		super();
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Long getDenomination() {
		return denomination;
	}

	public void setDenomination(Long denomination) {
		this.denomination = denomination;
	}

	public String getSellDate() {
		return sellDate;
	}

	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
