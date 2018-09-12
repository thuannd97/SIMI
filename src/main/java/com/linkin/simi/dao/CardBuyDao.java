package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.CardBuy;
import com.linkin.simi.model.SearchCardBuyDTO;

public interface CardBuyDao {
	
	public List<CardBuy> find(SearchCardBuyDTO searchCardBuyDTO);

	public long count(SearchCardBuyDTO searchCardBuyDTO);

	public void addCardBuy(CardBuy cardBuy);

	public void deteleCardBuy(CardBuy cardBuy);

	public void updateCardBuy(CardBuy cardBuy);

	public CardBuy getCardBuyById(Long id);
	
}
