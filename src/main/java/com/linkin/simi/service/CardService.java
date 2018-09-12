package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchCardDTO;
import com.linkin.simi.model.CardDTO;

public interface CardService {
	
	public List<CardDTO> find(SearchCardDTO searchCardDTO);

	public long count(SearchCardDTO searchCardDTO);

	public void deteleCard(Long id);

	public void updateCard(CardDTO cardDTO);

	public CardDTO getCardById(Long id);

	public void addCard(CardDTO cardDTO);
	
}
