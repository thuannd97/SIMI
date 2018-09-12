package com.linkin.simi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkin.simi.exception.NotAvailableSimException;
import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.SimBuyDTO;
import com.linkin.simi.model.UserDTO;
import com.linkin.simi.service.SimBuyService;
import com.linkin.simi.service.UserService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class SimBuyController {

	@Autowired
	private SimBuyService simBuyService;

	@Autowired
	private UserService userService;

	@GetMapping(URLConstant.STAFF + "/sim/buy/list")
	public String findSimBuys() {
		return "admin/sim/list-simbuy";
	}

	@PostMapping(URLConstant.STAFF + "/sim/buy/list")
	public ResponseEntity<ResponseDTO<SimBuyDTO>> findSimBuys(@RequestBody SearchSimBuyDTO searchSimBuyDTO) {
		ResponseDTO<SimBuyDTO> responseDTO = new ResponseDTO<SimBuyDTO>();
		responseDTO.setData(simBuyService.find(searchSimBuyDTO));
		long total = simBuyService.count(searchSimBuyDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<SimBuyDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(URLConstant.STAFF + "/sim/buy/add")
	public String buySim(Model model) {
		model.addAttribute("buyers", getBuyers());
		model.addAttribute("simBuy", new SimBuyDTO());
		return "admin/sim/buySim";
	}

	@PostMapping(value = URLConstant.STAFF + "/sim/buy/add")
	public String buySim(Model model, @ModelAttribute(name = "simBuy") SimBuyDTO simBuyDTO,
			BindingResult bindingResult) {
		// validate
		this.validateSim(simBuyDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("buyers", getBuyers());
			return "admin/sim/buySim";
		}
		try {
			simBuyService.addSimBuy(simBuyDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("simNo", "error.msg.sim.not.available");
			model.addAttribute("buyers", getBuyers());
			return "admin/sim/buySim";
		}
		return "redirect:/staff/sim/buy/list";
	}

	@GetMapping(URLConstant.ADMIN + "/sim/buy/edit/{id}")
	public String editSimBuy(Model model, @PathVariable(name = "id") Long id) {
		SimBuyDTO simBuyDTO = simBuyService.getSimBuyById(id);
		model.addAttribute("simBuy", simBuyDTO);
		model.addAttribute("buyers", getBuyers());
		return "admin/sim/editSimBuy";
	}

	@PostMapping(URLConstant.ADMIN + "/sim/buy/edit")
	public String editSimBuy(@ModelAttribute("simBuy") SimBuyDTO simBuyDTO, BindingResult bindingResult) {
		// validate
		this.validateSim(simBuyDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/sim/editSimBuy";
		}
		try {
			simBuyService.updateSimBuy(simBuyDTO);
		} catch (DataIntegrityViolationException ex) {
			return "admin/sim/editSimBuy";
		}
		return "redirect:/staff/sim/buy/list";
	}

	@GetMapping(URLConstant.ADMIN + "/sim/buy/delete/{id}")
	public ResponseEntity<String> deleteSimBuy(@PathVariable(name = "id") Long id) {
		simBuyService.deteleSimBuy(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	private List<UserDTO> getBuyers() {
		SearchUserDTO searchUserDTO = new SearchUserDTO();
		searchUserDTO.setPageSize(SearchUserDTO.MAX_100);
		return userService.findUsers(searchUserDTO);
	}

	private void validateSim(Object object, Errors errors) {
		SimBuyDTO simBuyDTO = (SimBuyDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "simNo", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cost", "error.msg.empty.sim.price");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "buyDate", "error.msg.empty.sim.date");
		if (!simBuyDTO.getSimNo().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("simNo", "error.msg.invalid.phone");
		}
	}

}
