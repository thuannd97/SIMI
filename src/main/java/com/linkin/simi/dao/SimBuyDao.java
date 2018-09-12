package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.SimBuy;
import com.linkin.simi.model.SearchSimBuyDTO;

public interface SimBuyDao {

	public List<SimBuy> find(SearchSimBuyDTO searchSimBuyDTO);

	public long count(SearchSimBuyDTO searchSimBuyDTO);

	public void addSimBuy(SimBuy sim);

	public void deteleSimBuy(SimBuy sim);

	public void updateSimBuy(SimBuy sim);

	public SimBuy getSimBuyById(Long id);

	public Long sum(SearchSimBuyDTO searchSimBuyDTO);
}
