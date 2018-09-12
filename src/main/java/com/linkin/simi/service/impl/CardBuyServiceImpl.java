package com.linkin.simi.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.CardBuyDao;
import com.linkin.simi.dao.CardDao;
import com.linkin.simi.entity.Card;
import com.linkin.simi.entity.CardBuy;
import com.linkin.simi.model.CardBuyDTO;
import com.linkin.simi.model.SearchCardBuyDTO;
import com.linkin.simi.service.CardBuyService;
import com.linkin.simi.utils.DateTimeUtils;

@Service
@Transactional
public class CardBuyServiceImpl implements CardBuyService {

	@Autowired
	private CardBuyDao cardBuyDao;
	@Autowired
	private CardDao cardDao;

	@Override
	public List<CardBuyDTO> find(SearchCardBuyDTO searchCardBuyDTO) {
		return null;
	}

	@Override
	public long count(SearchCardBuyDTO searchCardBuyDTO) {
		return 0;
	}

	@Override
	public void deteleCardBuy(Long id) {
	}

	@Override
	public void updateCardBuy(CardBuyDTO cardBuyDTO) {
	}

	@Override
	public CardBuyDTO getCardBuyById(Long id) {
		return null;
	}

	@Override
	public void addCard(CardBuyDTO cardBuyDTO) {
		Card card = cardDao.getById(cardBuyDTO.getCardId());
		if (card != null) {
			CardBuy cardBuy = new CardBuy();
			cardBuy.setCard(new Card(cardBuyDTO.getCardId()));
			cardBuy.setBuyDate(DateTimeUtils.parseDate(cardBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
			cardBuy.setCardType(cardBuyDTO.getCardType());
			cardBuy.setQuantity(cardBuyDTO.getQuantity());
			cardBuy.setUnitPrice(cardBuyDTO.getUnitPrice());

			card.getCardBuys().add(cardBuy);
			card.setQuantity(card.getQuantity() - cardBuyDTO.getQuantity());
			cardDao.update(card);
			cardBuyDao.addCardBuy(cardBuy);
		}
	}

}
