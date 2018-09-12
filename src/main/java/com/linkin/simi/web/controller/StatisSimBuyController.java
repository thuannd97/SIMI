package com.linkin.simi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.StatisSimBuyDTO;
import com.linkin.simi.service.StatisSimBuyService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class StatisSimBuyController {

	@Autowired
	private StatisSimBuyService statisSimBuyService;

	@GetMapping(URLConstant.STAFF + "/sim/buy/statis")
	public String getStatisSimBuy() {
		return "admin/sim/statis-sim-buy";
	}

	@PostMapping(URLConstant.STAFF + "/sim/buy/statis")
	public ResponseEntity<ResponseDTO<StatisSimBuyDTO>> getStatisSimBuy(@RequestBody SearchSimBuyDTO searchSimBuyDTO) {
		ResponseDTO<StatisSimBuyDTO> responseDTO = new ResponseDTO<StatisSimBuyDTO>();
		List<StatisSimBuyDTO> statisSimBuyDTO = statisSimBuyService.getStatisSimBuy(searchSimBuyDTO);
		responseDTO.setData(statisSimBuyDTO);
		return new ResponseEntity<ResponseDTO<StatisSimBuyDTO>>(responseDTO, HttpStatus.OK);
	}

}
