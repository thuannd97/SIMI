package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.CardDao;
import com.linkin.simi.entity.Card;
import com.linkin.simi.model.CardDTO;
import com.linkin.simi.model.SearchCardDTO;
import com.linkin.simi.service.CardService;
import com.linkin.simi.utils.DateTimeUtils;

@Service
@Transactional
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDao cardDao;

	@Override
	public List<CardDTO> find(SearchCardDTO searchCardDTO) {
		List<Card> cards = cardDao.find(searchCardDTO);
		List<CardDTO> cardDTOs = new ArrayList<CardDTO>();
		cards.forEach(card -> {
			CardDTO cardDTO = new CardDTO();
			cardDTO.setId(card.getId());
			cardDTO.setCardType(card.getCardType());
			cardDTO.setDenomination(card.getDenomination());
			cardDTO.setQuantity(card.getQuantity());
			cardDTO.setCreatedByName(card.getCreatedBy().getName());
			cardDTO.setCreatedDate(DateTimeUtils.formatDate(card.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			if (card.getUpdatedDate() != null) {
				cardDTO.setUpdatedDate(DateTimeUtils.formatDate(card.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			}
			cardDTO.setUpdatedByName(card.getUpdatedBy().getName());
			cardDTOs.add(cardDTO);
		});
		return cardDTOs;
	}

	@Override
	public long count(SearchCardDTO searchCardDTO) {
		return cardDao.count(searchCardDTO);
	}

	@Override
	public void deteleCard(Long id) {
		Card card = cardDao.getById(id);
		if (card != null) {
			cardDao.detele(card);
		}
	}

	@Override
	public void updateCard(CardDTO cardDTO) {
		Card card = cardDao.getById(cardDTO.getId());
		if (card != null) {
			card.setCardType(cardDTO.getCardType());
			card.setQuantity(cardDTO.getQuantity());
			card.setDenomination(cardDTO.getDenomination());

			cardDao.update(card);
		}
	}

	@Override
	public CardDTO getCardById(Long id) {
		Card card = cardDao.getById(id);
		if (card != null) {
			CardDTO cardDTO = new CardDTO();
			cardDTO.setId(card.getId());
			cardDTO.setCardType(card.getCardType());
			cardDTO.setDenomination(card.getDenomination());
			cardDTO.setQuantity(card.getQuantity());
			cardDTO.setCreatedByName(card.getCreatedBy().getName());
			cardDTO.setCreatedDate(DateTimeUtils.formatDate(card.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			if (card.getUpdatedDate() != null) {
				cardDTO.setUpdatedDate(DateTimeUtils.formatDate(card.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			}
			cardDTO.setUpdatedByName(card.getUpdatedBy().getName());
			return cardDTO;
		}
		return null;
	}

	@Override
	public void addCard(CardDTO cardDTO) {
		Card card = new Card();
		card.setCardType(cardDTO.getCardType());
		card.setQuantity(cardDTO.getQuantity());
		card.setDenomination(cardDTO.getDenomination());

		cardDao.add(card);
		cardDTO.setId(card.getId());
	}

}
