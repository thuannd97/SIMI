package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.StatisSimBuyDTO;

public interface StatisSimBuyService {

	public List<StatisSimBuyDTO> getStatisSimBuy(SearchSimBuyDTO searchSimBuyDTO);

}
