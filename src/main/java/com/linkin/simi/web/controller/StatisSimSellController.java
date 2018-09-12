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
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.StatisSimSellDTO;
import com.linkin.simi.service.StatisSimSellService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class StatisSimSellController {

	@Autowired
	private StatisSimSellService statisSimSellService;

	@GetMapping(URLConstant.STAFF + "/sim/sell/statis")
	public String getStatisSimSell() {
		return "admin/sim/statis-sim-sell";
	}

	@PostMapping(URLConstant.STAFF + "/sim/sell/statis")
	public ResponseEntity<ResponseDTO<StatisSimSellDTO>> getStatisSimSell(
			@RequestBody SearchSimSellDTO searchSimSellDTO) {
		ResponseDTO<StatisSimSellDTO> responseDTO = new ResponseDTO<StatisSimSellDTO>();
		List<StatisSimSellDTO> statisSimSellDTO = statisSimSellService.getStatisSimSell(searchSimSellDTO);
		responseDTO.setData(statisSimSellDTO);
		return new ResponseEntity<ResponseDTO<StatisSimSellDTO>>(responseDTO, HttpStatus.OK);
	}

}
