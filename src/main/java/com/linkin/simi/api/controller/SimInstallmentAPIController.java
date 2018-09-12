package com.linkin.simi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.InstallmentDTO;
import com.linkin.simi.model.PayTypeDTO;
import com.linkin.simi.model.SearchInstallmentDTO;
import com.linkin.simi.model.SearchPayTypeDTO;
import com.linkin.simi.service.SimInstallmentService;
import com.linkin.simi.service.SimPayTypeService;

@RestController
@RequestMapping(value = "/api")
public class SimInstallmentAPIController {

	@Autowired
	private SimInstallmentService simInstallmentService;
	
	@Autowired
	private SimPayTypeService  simPayTypeService;
	
	@PostMapping("/sim-installment")
	public ResponseEntity<List<InstallmentDTO>> findInstallment(@RequestBody SearchInstallmentDTO searchInstallmentDTO) {
		List<InstallmentDTO> simDTOs = simInstallmentService.find(searchInstallmentDTO);
		if (simDTOs == null) {
			return new ResponseEntity<List<InstallmentDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<InstallmentDTO>>(simDTOs, HttpStatus.OK);
		}
	}
	@PutMapping("/add-installment")
	public InstallmentDTO add(InstallmentDTO installmentDTO ) {
		simInstallmentService.addInstallment(installmentDTO);
		return installmentDTO;
	}
	@DeleteMapping("/delete-installment/{id}")
	public void detele(@PathVariable(name="id") Long id) {
		simInstallmentService.deteleInstallment(id);
		
	}
	@PostMapping("/paytype")
	public ResponseEntity<List<PayTypeDTO>> findPayType(@RequestBody SearchPayTypeDTO searchPayTypeDTO ){
		List<PayTypeDTO> listPayTypeDTO=simPayTypeService.find(searchPayTypeDTO);
		
		if(listPayTypeDTO==null) {
			return new ResponseEntity<List<PayTypeDTO>>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<List<PayTypeDTO>>(listPayTypeDTO,HttpStatus.OK);
		}	
	}
	@DeleteMapping("/Installment/detele/{id}")
	public void deleteSim(@PathVariable(name = "id") Long id) {
		simInstallmentService.deteleInstallment(id);
	}

}
