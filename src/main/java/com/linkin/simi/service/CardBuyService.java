package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.CardBuyDTO;
import com.linkin.simi.model.SearchCardBuyDTO;

public interface CardBuyService {
	
	public List<CardBuyDTO> find(SearchCardBuyDTO searchCardBuyDTO);

	public long count(SearchCardBuyDTO searchCardBuyDTO);

	public void deteleCardBuy(Long id);

	public void updateCardBuy(CardBuyDTO cardBuyDTO);

	public CardBuyDTO getCardBuyById(Long id);

	public void addCard(CardBuyDTO cardBuyDTO);
	
}
