package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.Installment;
import com.linkin.simi.model.SearchInstallmentDTO;

public interface SimInstallmentDao {
	public void add (Installment installment);

	public void detele(Installment installment);

	public void update(Installment installment);

	public Installment getById(Long id);

	List<Installment> find(SearchInstallmentDTO searchInstallmentSimDTO);

	long count(SearchInstallmentDTO searchInstallmentSimDTO);
	
}
