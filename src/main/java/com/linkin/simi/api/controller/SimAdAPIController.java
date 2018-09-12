package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchSimAdDTO;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.SimAdDTO;
import com.linkin.simi.model.StatisDTO;
import com.linkin.simi.model.StatisSearchDTO;
import com.linkin.simi.model.StatisSimBuyDTO;
import com.linkin.simi.model.StatisSimSellDTO;
import com.linkin.simi.service.SimAdService;
import com.linkin.simi.service.StatisService;
import com.linkin.simi.service.StatisSimBuyService;
import com.linkin.simi.service.StatisSimSellService;
import com.linkin.simi.utils.URLConstant;

@RestController
@RequestMapping("/api")
public class SimAdAPIController {

	@Autowired
	private SimAdService simAdService;

	@Autowired
	private StatisSimBuyService statisSimBuyService;

	@Autowired
	private StatisSimSellService statisSimSellService;

	@Autowired
	private StatisService statisService;

	@PostMapping("/simads")
	public ResponseEntity<ResponseDTO<SimAdDTO>> findSimAd(@RequestBody SearchSimAdDTO searchSimAdDTO) {
		ResponseDTO<SimAdDTO> responseDTO = new ResponseDTO<SimAdDTO>();
		long total = simAdService.countSimAd(searchSimAdDTO);
		responseDTO.setData(simAdService.findSimAd(searchSimAdDTO));
		responseDTO.setTotalRecords(total);
		responseDTO.setTotalRecords(total);
		return new ResponseEntity<ResponseDTO<SimAdDTO>>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(URLConstant.STAFF + "/sim/buy/statis")
	public ResponseEntity<List<StatisSimBuyDTO>> getStatisSimBuy(@RequestBody SearchSimBuyDTO searchSimBuyDTO) {
		List<StatisSimBuyDTO> statisSimBuyDTO = statisSimBuyService.getStatisSimBuy(searchSimBuyDTO);
		return new ResponseEntity<List<StatisSimBuyDTO>>(statisSimBuyDTO, HttpStatus.OK);
	}

	@PostMapping(URLConstant.STAFF + "/sim/sell/statis")
	public ResponseEntity<List<StatisSimSellDTO>> getStatisSimSell(@RequestBody SearchSimSellDTO searchSimSellDTO) {
		List<StatisSimSellDTO> statisSimSellDTO = statisSimSellService.getStatisSimSell(searchSimSellDTO);
		return new ResponseEntity<List<StatisSimSellDTO>>(statisSimSellDTO, HttpStatus.OK);
	}

	@PostMapping(URLConstant.STAFF + "/sim/statis")
	public ResponseEntity<ResponseDTO<StatisDTO>> getStatis(@RequestBody StatisSearchDTO statisSearchDTO) {
		ResponseDTO<StatisDTO> responseDTO = new ResponseDTO<StatisDTO>();
		List<StatisDTO> statisDTOs = statisService.getStatis(statisSearchDTO);
		responseDTO.setData(statisDTOs);
		return new ResponseEntity<ResponseDTO<StatisDTO>>(responseDTO, HttpStatus.OK);
	}

}
