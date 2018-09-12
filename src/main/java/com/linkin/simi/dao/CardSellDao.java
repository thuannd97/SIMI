package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.CardSell;
import com.linkin.simi.model.SearchCardSellDTO;

public interface CardSellDao {

	public void addCardSell(CardSell cardSell);

	public void deteleCardSell(CardSell cardSell);

	public void updateCardSell(CardSell cardSell);

	public CardSell getCardSellById(Long id);

	public long count(SearchCardSellDTO searchCardSellDTO);

	public List<CardSell> find(SearchCardSellDTO searchCardSellDTO);
	
}
