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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchSimAdDTO;
import com.linkin.simi.model.SimAdDTO;
import com.linkin.simi.service.SimAdService;

@Controller
@RequestMapping("/staff")
public class SimAdController {

	@Autowired
	private SimAdService simAdService;

	@GetMapping("/simads")
	public String findSimAd() {
		return "admin/simad/list-simads";
	}

	@PostMapping("/simads")
	public ResponseEntity<ResponseDTO<SimAdDTO>> findSimAd(@RequestBody SearchSimAdDTO searchSimAdDTO) {
		ResponseDTO<SimAdDTO> responseDTO = new ResponseDTO<SimAdDTO>();
		long total = simAdService.countSimAd(searchSimAdDTO);
		responseDTO.setData(simAdService.findSimAd(searchSimAdDTO));
		responseDTO.setTotalRecords(total);
		responseDTO.setTotalRecords(total);
		return new ResponseEntity<ResponseDTO<SimAdDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/simad/delete/{id}")
	public ResponseEntity<String> deleteSimAd(@PathVariable(name = "id") Long id) {
		simAdService.deleteSimAd(simAdService.getSimAd(id));
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/simad/add")
	public String addSimAd(Model model) {
		model.addAttribute("simAd", new SimAdDTO());
		return "admin/simad/add-simad";
	}

	@PostMapping("/simad/add")
	public String addSimAd(@ModelAttribute("simAd") SimAdDTO simAdDTO, BindingResult bindingResult) {
		simAdService.addSimAd(simAdDTO);
		return "redirect:/staff/simads";
	}

	@GetMapping("/simad/update/{id}")
	public String updateSimAd(Model model, @PathVariable(name = "id") Long id) {
		SimAdDTO simAdDTO = simAdService.getSimAd(id);
		model.addAttribute("simAdDTO", simAdDTO);
		return "admin/simad/update-simad";
	}

	@PostMapping("/simad/update")
	public String updateSimAd(@ModelAttribute("simDTO") SimAdDTO simAdDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/simad/update-simad";
		}
		try {
			simAdService.updateSimAd(simAdDTO);
		} catch (DataIntegrityViolationException ex) {
			return "admin/simad/update-simad";
		}
		return "redirect:/staff/simads";
	}

	@PutMapping("/simad/change-status")
	public SimAdDTO changeStatus(@RequestBody SimAdDTO simAdDTO) {
		simAdService.changeStatus(simAdDTO);
		return simAdDTO;
	}

}
