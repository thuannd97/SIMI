package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.SimSellDTO;

public interface SimSellService {

	public List<SimSellDTO> find(SearchSimSellDTO searchSimSellDTO);

	public void addSimSell(SimSellDTO item);

	public void deteleSimSell(Long id);

	public void updateSimSell(SimSellDTO item);

	public long count(SearchSimSellDTO searchSimSellDTO);
	
	SimSellDTO get(Long id);
}
