package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.linkin.simi.excel.ExcelBuilderSim;
import com.linkin.simi.model.SearchSimDTO;
import com.linkin.simi.model.SimDTO;
import com.linkin.simi.service.SimService;

@RestController
@RequestMapping(value = "/api")
public class SimAPIController {

	@Autowired
	private SimService serviceSim;

	@PostMapping("/staff/sims")
	public ResponseEntity<List<SimDTO>> findSim(@RequestBody SearchSimDTO searchSimDTO) {
		List<SimDTO> simDTOs = serviceSim.find(searchSimDTO);
		if (simDTOs == null) {
			return new ResponseEntity<List<SimDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<SimDTO>>(simDTOs, HttpStatus.OK);
		}
	}

	@PutMapping("/staff/sim")
	public SimDTO updateSim(@RequestBody SimDTO simDTO) {
		serviceSim.updateSim(simDTO);
		return simDTO;
	}

	@DeleteMapping("/admin/sim/{id}")
	public void deleteSim(@PathVariable(name = "id") Long id) {
		serviceSim.deteleSim(id);
	}

	@GetMapping("/staff/sim/excel")
	public ModelAndView viewExcel() {
		return new ModelAndView(new ExcelBuilderSim(), "simDTOs", serviceSim.find(new SearchSimDTO()));
	}
}
