package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.linkin.simi.excel.ExcelBuilderSimBuy;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SimBuyDTO;
import com.linkin.simi.service.SimBuyService;

@RestController
@RequestMapping(value = "/api")
public class SimBuyAPIController {

	@Autowired
	private SimBuyService serviceSimBuy;
	
	@PostMapping("/simBuys")
	public ResponseEntity<List<SimBuyDTO>> findSimBuy(@RequestBody SearchSimBuyDTO searchSimBuyDTO) {
		List<SimBuyDTO> lists=serviceSimBuy.find(searchSimBuyDTO);
		if(lists==null) {
			return new ResponseEntity<List<SimBuyDTO>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<SimBuyDTO>>(lists,HttpStatus.OK);
		}
	}

	@PostMapping("/addsimBuy")
	public SimBuyDTO addSimBuy(@RequestBody SimBuyDTO simBuyDTO) {
		serviceSimBuy.addSimBuy(simBuyDTO);
		return simBuyDTO;
	}
	
	@GetMapping("/simBuy/excel")
	public ModelAndView viewExcel() {
		return new ModelAndView(new ExcelBuilderSimBuy(), "simBuyDTOs", serviceSimBuy.find(new SearchSimBuyDTO()));
	}
}
