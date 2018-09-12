package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.SimSell;
import com.linkin.simi.model.SearchSimSellDTO;


public interface SimSellDao {

	public void addSimSell(SimSell sim);

	public void deteleSimSell(SimSell sim);

	public void updateSimSell(SimSell sim);

	public SimSell getSimSaleById(Long id);

	public long count(SearchSimSellDTO searchSimSellDTO);

	public List<SimSell> find(SearchSimSellDTO searchSimSellDTO);

	public Long sum(SearchSimSellDTO searchSimSellDTO);

	public Long sumDeposit(SearchSimSellDTO searchSimSellDTO);
}
