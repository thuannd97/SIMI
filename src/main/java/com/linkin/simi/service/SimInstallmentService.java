package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.InstallmentDTO;
import com.linkin.simi.model.SearchInstallmentDTO;

public interface SimInstallmentService {

	public List<InstallmentDTO> find(SearchInstallmentDTO searchInstallmentDTO);

	public long count(SearchInstallmentDTO searchInstallmentDTO);

	public void addInstallment(InstallmentDTO installmentDTO );

	public void deteleInstallment(Long id);

	public void updateInstallment(InstallmentDTO installmentDTO);

	public InstallmentDTO getInstallmentById(Long id);

	
}
