package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.InfoInstallment;
import com.linkin.simi.model.SearchInfoInstallmentDTO;


public interface SimInfoInstallmentDao {
	
	public void add (InfoInstallment infoInstallment);

	public void detele(InfoInstallment infoInstallment);

	public void update(InfoInstallment infoInstallment);

	public InfoInstallment getById(int id);

	List<InfoInstallment> find(SearchInfoInstallmentDTO searchInfoInstallmentDTO);

	long count(SearchInfoInstallmentDTO searchInfoInstallmentDTO);
	
	public InfoInstallment getByIdInfostallment(Long idInstallment);
	
}
