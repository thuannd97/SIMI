package com.linkin.simi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.SimSellDTO;
import com.linkin.simi.model.UserDTO;
import com.linkin.simi.service.SimSellService;
import com.linkin.simi.service.UserService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class SimSellController {

	@Autowired
	private SimSellService simSellService;

	@Autowired
	private UserService userService;

	@GetMapping(URLConstant.STAFF + "/sim/sell/list")
	public String listSimSell() {
		return "admin/sim/list-simsell";
	}

	@PostMapping(value = URLConstant.STAFF + "/sim/sell/list")
	public ResponseEntity<ResponseDTO<SimSellDTO>> findSimSells(@RequestBody SearchSimSellDTO searchSimSellDTO) {
		ResponseDTO<SimSellDTO> responseDTO = new ResponseDTO<SimSellDTO>();
		responseDTO.setData(simSellService.find(searchSimSellDTO));
		long total = simSellService.count(searchSimSellDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<SimSellDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(URLConstant.STAFF + "/sim/sell/add")
	public String sellSim(Model model) {
		model.addAttribute("sellers", getSellers());
		model.addAttribute("simsell", new SimSellDTO());
		return "admin/sim/sellSim";
	}

	@GetMapping(URLConstant.ADMIN + "/sim/sell/edit/{id}")
	public String editSimSell(Model model, @PathVariable(name = "id") Long id) {
		SimSellDTO simSellDTO = simSellService.get(id);
		model.addAttribute("sellers", getSellers());
		model.addAttribute("simsell", simSellDTO);
		return "admin/sim/editSimSell";
	}

	@PostMapping(value = URLConstant.ADMIN + "/sim/sell/edit")
	public String editSimSell(Model model, @ModelAttribute(name = "simsell") SimSellDTO simSellDTO,
			BindingResult bindingResult) {
		// validate
		this.validateSim(simSellDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("sellers", getSellers());
			return "admin/sim/editSimSell";
		}
		try {
			simSellService.updateSimSell(simSellDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("simNo", "error.msg.sim.not.available");
			model.addAttribute("sellers", getSellers());
			return "admin/sim/editSimSell";
		}
		return "redirect:/staff/sim/sell/list";
	}

	@PostMapping(value = URLConstant.STAFF + "/sim/sell/add")
	public String sellSim(Model model, @ModelAttribute(name = "simsell") SimSellDTO simSellDTO,
			BindingResult bindingResult) {
		// validate
		this.validateSim(simSellDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("sellers", getSellers());
			return "admin/sim/sellSim";
		}
		try {
			simSellService.addSimSell(simSellDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("simNo", "error.msg.sim.not.available");
			model.addAttribute("sellers", getSellers());
			return "admin/sim/sellSim";
		}
		return "redirect:/staff/sim/sell/list";
	}

	@GetMapping(URLConstant.ADMIN + "/sim/sell/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
		simSellService.deteleSimSell(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	private List<UserDTO> getSellers() {
		SearchUserDTO searchUserDTO = new SearchUserDTO();
		searchUserDTO.setPageSize(SearchUserDTO.MAX_100);
		return userService.findUsers(searchUserDTO);
	}

	private void validateSim(Object object, Errors errors) {
		SimSellDTO simSellDTO = (SimSellDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "simNo", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sellPrice", "error.msg.empty.sim.price");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sellDate", "error.msg.empty.sim.date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "error.msg.empty.sim.customer");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deposit", "error.msg.empty.sim.deposit");
		if (!simSellDTO.getSimNo().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("simNo", "error.msg.invalid.phone");
		}
	}
}
