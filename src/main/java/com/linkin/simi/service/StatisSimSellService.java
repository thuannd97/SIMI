package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.StatisSimSellDTO;

public interface StatisSimSellService {
	
	public List<StatisSimSellDTO> getStatisSimSell(SearchSimSellDTO searchSimSellDTO);
	
}
