package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.CardSellDTO;
import com.linkin.simi.model.SearchCardSellDTO;

public interface CardSellService {

	public List<CardSellDTO> find(SearchCardSellDTO searchCardSellDTO);

	public long count(SearchCardSellDTO searchCardSellDTO);

	public void deteleCardBuy(Long id);

	public void updateCardBuy(CardSellDTO cardSellDTO);

	public CardSellDTO getCardBuyById(Long id);

	public void addCard(CardSellDTO cardSellDTO);

}
