package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimBuyDao;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.StatisSimBuyDTO;
import com.linkin.simi.service.StatisSimBuyService;

@Service
@Transactional
public class StatisSimBuyServiceImpl implements StatisSimBuyService {

	@Autowired
	private SimBuyDao simBuyDao;

	@Override
	public List<StatisSimBuyDTO> getStatisSimBuy(SearchSimBuyDTO searchSimBuyDTO) {
		List<StatisSimBuyDTO> statisSimBuyDTOs = new ArrayList<StatisSimBuyDTO>();
		StatisSimBuyDTO statisSimBuyDTO = new StatisSimBuyDTO();
		statisSimBuyDTO.setQuantity(simBuyDao.count(searchSimBuyDTO));
		statisSimBuyDTO.setTotalMoney(simBuyDao.sum(searchSimBuyDTO));
		statisSimBuyDTOs.add(statisSimBuyDTO);
		return statisSimBuyDTOs;
	}

}
