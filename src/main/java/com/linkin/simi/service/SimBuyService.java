package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SimBuyDTO;

public interface SimBuyService {
	public List<SimBuyDTO> find(SearchSimBuyDTO searchSimBuyDTO);

	public void addSimBuy(SimBuyDTO sim);

	public void deteleSimBuy(Long id);

	public void updateSimBuy(SimBuyDTO simBuyDTO);
	
	public long count(SearchSimBuyDTO searchSimBuyDTO);
	
	public SimBuyDTO getSimBuyById(Long id);
}
