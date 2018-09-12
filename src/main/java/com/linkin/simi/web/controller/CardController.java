package com.linkin.simi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.simi.exception.NotAvailableSimException;
import com.linkin.simi.model.CardDTO;
import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchCardDTO;
import com.linkin.simi.service.CardService;

@Controller
@RequestMapping("/admin")
public class CardController {

	@Autowired
	private CardService cardService;

	@GetMapping("/card/add")
	public String addCard(Model model) {
		model.addAttribute("card", new CardDTO());
		return "admin/card/addCard";
	}

	@PostMapping(value = "/card/add")
	public String addCard(Model model, @ModelAttribute(name = "card") CardDTO cardDTO, BindingResult bindingResult) {
		// validate
		if (bindingResult.hasErrors()) {
			return "admin/card/addCard";
		}
		try {
			cardService.addCard(cardDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("cardNo", "error.msg.card.not.available");
			return "admin/card/addCard";
		}
		return "redirect:/admin/cards";
	}

	@GetMapping("/cards")
	public String listCards() {
		return "admin/card/listCard";
	}

	@PostMapping(value = "/cards")
	public ResponseEntity<ResponseDTO<CardDTO>> findCard(@RequestBody SearchCardDTO searchCardDTO) {
		ResponseDTO<CardDTO> responseDTO = new ResponseDTO<CardDTO>();
		responseDTO.setData(cardService.find(searchCardDTO));
		long total = cardService.count(searchCardDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<CardDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/card/delete/{id}")
	public ResponseEntity<String> deleteCard(@PathVariable(name = "id") Long id) {
		cardService.deteleCard(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/card/update/{id}")
	public String updateCard(Model model, @PathVariable(name = "id") Long id) {
		CardDTO cardDTO = cardService.getCardById(id);
		model.addAttribute("cardDTO", cardDTO);
		return "admin/card/update-card";
	}

	@PostMapping("/card/update")
	public String updateCard(@ModelAttribute(name = "cardDTO") CardDTO cardDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/card/update-card";
		}
		try {
			cardService.updateCard(cardDTO);
		} catch (DataIntegrityViolationException ex) {
			return "admin/card/update-card";
		}
		return "redirect:/admin/cards";
	}

}
