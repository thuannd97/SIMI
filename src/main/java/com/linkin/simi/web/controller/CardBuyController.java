package com.linkin.simi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.linkin.simi.exception.NotAvailableSimException;
import com.linkin.simi.model.CardBuyDTO;
import com.linkin.simi.model.SearchCardDTO;
import com.linkin.simi.service.CardBuyService;
import com.linkin.simi.service.CardService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class CardBuyController {

	@Autowired
	private CardBuyService cardBuyService;
	@Autowired
	private CardService cardService;;

	@GetMapping(URLConstant.STAFF + "/card/buy/add")
	public String buySim(Model model) {
		model.addAttribute("cards", cardService.find(new SearchCardDTO()));
		model.addAttribute("cardBuy", new CardBuyDTO());
		return "admin/card/buyCard";
	}

	@PostMapping(value = URLConstant.STAFF + "/card/buy/add")
	public String buySim(Model model, @ModelAttribute(name = "cardBuy") CardBuyDTO cardBuyDTO, BindingResult bindingResult) {
		// validate
		if (bindingResult.hasErrors()) {
			model.addAttribute("cards", cardService.find(new SearchCardDTO()));
			return "admin/card/buyCard";
		}
		try {
			cardBuyService.addCard(cardBuyDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("simNo", "error.msg.sim.not.available");
			model.addAttribute("cards", cardService.find(new SearchCardDTO()));
			return "admin/card/buyCard";
		}
		return "redirect:/staff/sim/buy/list";
	}
}
