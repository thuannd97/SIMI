package com.linkin.simi.model;

import java.io.Serializable;
import java.util.List;

public class CardDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int cardType;
	private Long denomination;
	private Long quantity;
	private List<CardBuyDTO> cardBuyDTOs;
	private List<CardSellDTO> cardSellDTOs;
	private String createdByName;
	private String createdDate;
	private String updatedByName;
	private String updatedDate;

	public CardDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public Long getDenomination() {
		return denomination;
	}

	public void setDenomination(Long denomination) {
		this.denomination = denomination;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public List<CardBuyDTO> getCardBuyDTOs() {
		return cardBuyDTOs;
	}

	public void setCardBuyDTOs(List<CardBuyDTO> cardBuyDTOs) {
		this.cardBuyDTOs = cardBuyDTOs;
	}

	public List<CardSellDTO> getCardSellDTOs() {
		return cardSellDTOs;
	}

	public void setCardSellDTOs(List<CardSellDTO> cardSellDTOs) {
		this.cardSellDTOs = cardSellDTOs;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
