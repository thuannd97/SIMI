package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.InfoInstallmentDTO;
import com.linkin.simi.model.SearchInfoInstallmentDTO;

public interface SimInfoInstallmentService {
	
	public List<InfoInstallmentDTO> find(SearchInfoInstallmentDTO searchInfoInstallmentDTO,int id);
	
	public List<InfoInstallmentDTO> findapi(SearchInfoInstallmentDTO searchInfoInstallmentDTO) ;

	public long count(SearchInfoInstallmentDTO searchInfoInstallmentDTO);

	public void addInfoInstallment(InfoInstallmentDTO infoInstallmentDTO );

	public void deteleInfoInstallment(int id);

	public void updateInfoInstallment(InfoInstallmentDTO infoInstallmentDTO);

	public InfoInstallmentDTO getInfoInstallmentById(int id);
}
