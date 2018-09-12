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

import com.linkin.simi.excel.ExcelBuilderSimSell;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.SimSellDTO;
import com.linkin.simi.service.SimSellService;

@RestController
@RequestMapping(value = "/api")
public class SimSellAPIController {

	
	@Autowired
	private SimSellService serviceSimSell;
	

	@PostMapping("/simSells")
	public ResponseEntity<List<SimSellDTO>> findSimSell(@RequestBody SearchSimSellDTO searchSimSellDTO){
		List<SimSellDTO> lists=serviceSimSell.find(searchSimSellDTO);
		if(lists==null) {
			return new ResponseEntity<List<SimSellDTO>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<SimSellDTO>>(lists,HttpStatus.OK);
		}
		
	}

	@PostMapping("/addsimSell")
	public SimSellDTO addSimSale(@RequestBody SimSellDTO simSellDTO) {
		serviceSimSell.addSimSell(simSellDTO);
		return simSellDTO;
	}

	@GetMapping("/simSell/excel")
	public ModelAndView viewExcel() {
		return new ModelAndView(new ExcelBuilderSimSell(), "simSellDTOs", serviceSimSell.find(new SearchSimSellDTO()));
	}
}
