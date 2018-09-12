package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.CardDTO;
import com.linkin.simi.model.SearchCardDTO;
import com.linkin.simi.service.CardService;

@RestController
@RequestMapping("/api")
public class CardAPIController {
	
	@Autowired
	private CardService cardService;
	
	@PostMapping("/staff/card")
	public CardDTO addCard(@RequestBody CardDTO cardDTO) {
		cardService.addCard(cardDTO);
		return cardDTO;
	}
	
	@PostMapping("/staff/cards")
	public ResponseEntity<List<CardDTO>> findCard(@RequestBody SearchCardDTO searchCardDTO) {
		List<CardDTO> cardDTOs = cardService.find(searchCardDTO);
		if (cardDTOs == null) {
			return new ResponseEntity<List<CardDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<CardDTO>>(cardDTOs, HttpStatus.OK);
		}
	}

	@PutMapping("/staff/card")
	public CardDTO updateCard(@RequestBody CardDTO cardDTO) {
		cardService.updateCard(cardDTO);
		return cardDTO;
	}

	@DeleteMapping("/admin/card/{id}")
	public void deleteCard(@PathVariable(name = "id") Long id) {
		cardService.deteleCard(id);
	}
}
