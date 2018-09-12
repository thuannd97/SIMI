package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.Card;
import com.linkin.simi.model.SearchCardDTO;

public interface CardDao {
	
	public void add(Card card);

	public void detele(Card card);

	public void update(Card card);

	public Card getById(Long id);

	List<Card> find(SearchCardDTO searchCardDTO);

	long count(SearchCardDTO searchCardDTO);

}
