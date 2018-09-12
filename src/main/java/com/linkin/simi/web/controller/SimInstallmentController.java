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
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.simi.exception.NotAvailableSimException;
import com.linkin.simi.model.InstallmentDTO;
import com.linkin.simi.model.PayTypeDTO;
import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchInstallmentDTO;
import com.linkin.simi.model.SearchPayTypeDTO;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.service.SimInstallmentService;
import com.linkin.simi.service.SimPayTypeService;

@Controller
@RequestMapping("/admin")
public class SimInstallmentController {

	@Autowired
	SimInstallmentService simInstallmentService;
	@Autowired
	SimPayTypeService simPayTypeService;

	@GetMapping("/list-installments")
	public String listSim(Model model) {
		return "admin/sim/list-installment";
	}

	@PostMapping( "/list-installments")
	public ResponseEntity<ResponseDTO<InstallmentDTO>> findInstallmentDTO(
			@RequestBody SearchInstallmentDTO searchInstallmentDTO) {
		ResponseDTO<InstallmentDTO> responseDTO = new ResponseDTO<InstallmentDTO>();
		responseDTO.setData(simInstallmentService.find(searchInstallmentDTO));
		long total = simInstallmentService.count(searchInstallmentDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<InstallmentDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/installment/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		simInstallmentService.deteleInstallment(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/installment/add")
	public String add(Model model) {
		model.addAttribute("simInstallment", new InstallmentDTO());
		model.addAttribute("paytype", getPayTypes());
		return "admin/sim/installmentSim";
	}

	@PostMapping("/installment/add")
	public String addP(Model model, @ModelAttribute InstallmentDTO installmentDTO, BindingResult bindingResult) {
		this.validateSimInstallment(installmentDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("simInstallment", installmentDTO);
		}
		try {
			simInstallmentService.addInstallment(installmentDTO);
		} catch (NotAvailableSimException exception) {
			bindingResult.rejectValue("simNo", "error.msg.sim.not.available");
			model.addAttribute("paytype", getPayTypes());
			return "admin/sim/installmentSim";
			
		}
		return "redirect:/admin/list-installments";
	}

	private List<PayTypeDTO> getPayTypes() {
		SearchPayTypeDTO searchPayType = new SearchPayTypeDTO();
		searchPayType.setPageSize(SearchUserDTO.MAX_100);
		return simPayTypeService.find(searchPayType);
	}
	

	private void validateSimInstallment(Object object, Errors errors) {
		InstallmentDTO installmentDTO = (InstallmentDTO) object;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "simNo", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPhone", "error.msg.empty.account.phone");

		if (!installmentDTO.getSimNo().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("simNo", "error.msg.invalid.phone");
		}
	}

}
