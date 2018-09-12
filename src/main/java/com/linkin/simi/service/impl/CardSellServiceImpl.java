package com.linkin.simi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.linkin.simi.model.CardSellDTO;
import com.linkin.simi.model.SearchCardSellDTO;
import com.linkin.simi.service.CardSellService;

@Service
@Transactional
public class CardSellServiceImpl implements CardSellService {

	@Override
	public List<CardSellDTO> find(SearchCardSellDTO searchCardSellDTO) {
		return null;
	}

	@Override
	public long count(SearchCardSellDTO searchCardSellDTO) {
		return 0;
	}

	@Override
	public void deteleCardBuy(Long id) {
	}

	@Override
	public void updateCardBuy(CardSellDTO cardSellDTO) {
	}

	@Override
	public CardSellDTO getCardBuyById(Long id) {
		return null;
	}

	@Override
	public void addCard(CardSellDTO cardSellDTO) {
	}

}
