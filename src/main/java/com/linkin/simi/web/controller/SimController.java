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

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchSimDTO;
import com.linkin.simi.model.SimDTO;
import com.linkin.simi.service.SimService;

@Controller
@RequestMapping("/admin")
public class SimController {

	@Autowired
	private SimService simService;

	@GetMapping("/sims")
	public String listSim(Model model) {
		return "admin/sim/list-sim";
	}

	@PostMapping(value = "/sims")
	public ResponseEntity<ResponseDTO<SimDTO>> find(
			@RequestBody SearchSimDTO searchSimDTO) {
		ResponseDTO<SimDTO> responseDTO = new ResponseDTO<SimDTO>();
		responseDTO.setData(simService.find(searchSimDTO));
		long total = simService.count(searchSimDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<SimDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/sim/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		simService.deteleSim(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}


	@GetMapping("/sim/update/{id}")
	public String updateSim(Model model, @PathVariable(name = "id") Long id) {
		SimDTO simDTO = simService.getSimById(id);
		model.addAttribute("simDTO", simDTO);
		return "admin/sim/update-sim";
	}

	@PostMapping("/sim/update")
	public String updateSim(@ModelAttribute(name = "simDTO") SimDTO simDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/sim/update-sim";
		}
		try {
			simService.updateSim(simDTO);
		} catch (DataIntegrityViolationException ex) {
			return "admin/sim/update-sim";
		}
		return "redirect:/admin/sims";
	}

	@PostMapping("/sim/edit-price")
	public ResponseEntity<SimDTO> editPrice(@RequestBody SimDTO simDTO) {
		simService.editPrice(simDTO);
		return new ResponseEntity<SimDTO>(simDTO, HttpStatus.OK);
	}

}
