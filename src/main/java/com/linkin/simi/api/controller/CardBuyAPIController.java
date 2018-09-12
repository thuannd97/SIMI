package com.linkin.simi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.CardBuyDTO;
import com.linkin.simi.service.CardBuyService;

@RestController
@RequestMapping("/api")
public class CardBuyAPIController {
	
	@Autowired
	private CardBuyService cardBuyService;
	
	@PostMapping("/cardBuy")
	public CardBuyDTO addCard(@RequestBody CardBuyDTO cardBuyDTO) {
		cardBuyService.addCard(cardBuyDTO);
		return cardBuyDTO;
	}
}
