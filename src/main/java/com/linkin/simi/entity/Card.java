package com.linkin.simi.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private Long id;

	@Column(name = "card_type")
	private int cardType;
	@Column(name = "denomination")
	private Long denomination;
	@Column(name = "quantity")
	private Long quantity;

	// bi-directional many-to-one association to CardSell
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CardBuy> cardBuys;

	// bi-directional many-to-one association to CardSell
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CardSell> cardSells;

	public Card() {
	}

	public Card(Long id) {
		super();
		this.id = id;
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

	public List<CardBuy> getCardBuys() {
		return cardBuys;
	}

	public void setCardBuys(List<CardBuy> cardBuys) {
		this.cardBuys = cardBuys;
	}

	public List<CardSell> getCardSells() {
		return cardSells;
	}

	public void setCardSells(List<CardSell> cardSells) {
		this.cardSells = cardSells;
	}

	public CardBuy addCardBuy(CardBuy cardBuy) {
		getCardBuys().add(cardBuy);
		cardBuy.setCard(this);

		return cardBuy;
	}

	public CardBuy removeCardBuy(CardBuy cardBuy) {
		getCardBuys().remove(cardBuy);
		cardBuy.setCard(null);

		return cardBuy;
	}

	public CardSell addCardSell(CardSell cardSell) {
		getCardSells().add(cardSell);
		cardSell.setCard(this);

		return cardSell;
	}

	public CardSell removeCardSell(CardSell cardSell) {
		getCardSells().remove(cardSell);
		cardSell.setCard(null);

		return cardSell;
	}
}
