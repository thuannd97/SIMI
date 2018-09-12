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
import com.linkin.simi.model.StatisDTO;
import com.linkin.simi.model.StatisSearchDTO;
import com.linkin.simi.service.StatisService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class StatisController {

	@Autowired
	private StatisService statisService;

	@GetMapping(URLConstant.STAFF + "/sim/statis")
	public String getStatis() {
		return "admin/sim/statis";
	}

	@PostMapping(URLConstant.STAFF + "/sim/statis")
	public ResponseEntity<ResponseDTO<StatisDTO>> getStatis(@RequestBody StatisSearchDTO statisSearchDTO) {
		ResponseDTO<StatisDTO> responseDTO = new ResponseDTO<StatisDTO>();
		List<StatisDTO> statisDTOs = statisService.getStatis(statisSearchDTO);
		responseDTO.setData(statisDTOs);
		return new ResponseEntity<ResponseDTO<StatisDTO>>(responseDTO, HttpStatus.OK);
	}

}
