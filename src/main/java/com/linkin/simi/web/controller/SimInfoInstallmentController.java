package com.linkin.simi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.linkin.simi.model.InfoInstallmentDTO;
import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchInfoInstallmentDTO;
import com.linkin.simi.service.SimInfoInstallmentService;

@Controller
@RequestMapping("/admin")
public class SimInfoInstallmentController {

	@Autowired
	SimInfoInstallmentService simInfoInstallmentService;

	private int i;

	@PostMapping("/inforInstallment")
	public ResponseEntity<ResponseDTO<InfoInstallmentDTO>> list(
			@RequestBody SearchInfoInstallmentDTO searchInfoInstallmentDTO) {

		ResponseDTO<InfoInstallmentDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(simInfoInstallmentService.find(searchInfoInstallmentDTO, i));
		long total = simInfoInstallmentService.count(searchInfoInstallmentDTO);
		responseDTO.setRecordsFiltered(total);
		responseDTO.setTotalRecords(total);
		return new ResponseEntity<ResponseDTO<InfoInstallmentDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/payInstallment/{id}")
	public String pay(Model model, @PathVariable(name = "id") int id) {
		i = id;
		return "admin/sim/infoInstallment";
	}

	@GetMapping("/addInfoInstallment")
	public String add(Model model) {
		InfoInstallmentDTO infoInstallmentDTO = new InfoInstallmentDTO();
		model.addAttribute("infoInstallmentDTO", infoInstallmentDTO);

		return "admin/sim/pay-installment";
	}

	@PostMapping("/addInfoInstallment")
	public String addP(Model model, @ModelAttribute InfoInstallmentDTO infoInstallmentDTO,
			BindingResult bindingResult) {
		infoInstallmentDTO.setIdInstallment((long) i);
		infoInstallmentDTO.setTimes(simInfoInstallmentService.find(new SearchInfoInstallmentDTO(), i).size() + 1);
		simInfoInstallmentService.addInfoInstallment(infoInstallmentDTO);

		return "admin/sim/infoInstallment";
	}
	
}
