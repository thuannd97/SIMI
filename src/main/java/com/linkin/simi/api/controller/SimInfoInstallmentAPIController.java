package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.InfoInstallmentDTO;
import com.linkin.simi.model.SearchInfoInstallmentDTO;
import com.linkin.simi.service.SimInfoInstallmentService;

@RestController
@RequestMapping("/api")
public class SimInfoInstallmentAPIController {

	@Autowired
	SimInfoInstallmentService simInfoInstallmentService;

	@PostMapping("/infoInstallment")
	public ResponseEntity<List<InfoInstallmentDTO>> findInfoInstallment(
			@RequestBody SearchInfoInstallmentDTO searchInfoInstallmentDTO) {
		List<InfoInstallmentDTO> list = simInfoInstallmentService.findapi(searchInfoInstallmentDTO);
		if (list == null) {
			return new ResponseEntity<List<InfoInstallmentDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<InfoInstallmentDTO>>(list,HttpStatus.OK);	}

	}

}
