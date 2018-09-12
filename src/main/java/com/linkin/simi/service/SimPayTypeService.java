package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.PayTypeDTO;
import com.linkin.simi.model.SearchPayTypeDTO;

public interface SimPayTypeService {
	public List<PayTypeDTO> find(SearchPayTypeDTO searchPayTypeDTO);

	public long count(SearchPayTypeDTO searchPayTypeDTO);

	public void addPayType(PayTypeDTO payTypeDTO );

	public void detelepPayType(Long id);

	public void updatePayType(PayTypeDTO payTypeDTO);

	public PayTypeDTO getPayTypebyId(int id);

}
