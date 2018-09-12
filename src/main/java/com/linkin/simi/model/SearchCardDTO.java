package com.linkin.simi.model;

public class SearchCardDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private Long denomination;
	private Integer cardType;

	public SearchCardDTO() {
		super();
	}

	public Long getDenomination() {
		return denomination;
	}

	public void setDenomination(Long denomination) {
		this.denomination = denomination;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

}
