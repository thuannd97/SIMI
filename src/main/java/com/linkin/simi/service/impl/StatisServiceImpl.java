package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimBuyDao;
import com.linkin.simi.dao.SimSellDao;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.StatisDTO;
import com.linkin.simi.model.StatisSearchDTO;
import com.linkin.simi.service.StatisService;

@Service
@Transactional
public class StatisServiceImpl implements StatisService {

	@Autowired
	private SimSellDao simSellDao;

	@Autowired
	private SimBuyDao simBuyDao;

	@Override
	public List<StatisDTO> getStatis(StatisSearchDTO statisSearchDTO) {
		List<StatisDTO> statisDTOs = new ArrayList<StatisDTO>();
		StatisDTO statisDTO = new StatisDTO();

		SearchSimBuyDTO searchSimBuyDTO = new SearchSimBuyDTO();
		searchSimBuyDTO.setToDate(statisSearchDTO.getToDate());
		searchSimBuyDTO.setFromDate(statisSearchDTO.getFromDate());
		SearchSimSellDTO searchSimSellDTO = new SearchSimSellDTO();
		searchSimSellDTO.setToDate(statisSearchDTO.getToDate());
		searchSimSellDTO.setFromDate(statisSearchDTO.getFromDate());

		statisDTO.setQuantitySimBuy(simBuyDao.count(searchSimBuyDTO));
		statisDTO.setQuantitySimSell(simSellDao.count(searchSimSellDTO));
		statisDTO.setCostTotal(simBuyDao.sum(searchSimBuyDTO));
		statisDTO.setPriceTotal(simSellDao.sum(searchSimSellDTO));
		statisDTO.setDeposit(simSellDao.sumDeposit(searchSimSellDTO));
		statisDTOs.add(statisDTO);
		return statisDTOs;
	}

}
