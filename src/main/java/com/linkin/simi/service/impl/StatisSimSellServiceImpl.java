package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimSellDao;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.StatisSimSellDTO;
import com.linkin.simi.service.StatisSimSellService;

@Service
@Transactional
public class StatisSimSellServiceImpl implements StatisSimSellService {

	@Autowired
	private SimSellDao simSellDao;

	@Override
	public List<StatisSimSellDTO> getStatisSimSell(SearchSimSellDTO searchSimSellDTO) {
		List<StatisSimSellDTO> statisSimSellDTOs = new ArrayList<StatisSimSellDTO>();
		StatisSimSellDTO statisSimSellDTO = new StatisSimSellDTO();
		statisSimSellDTO.setQuantity(simSellDao.count(searchSimSellDTO));
		statisSimSellDTO.setTotalMoney(simSellDao.sum(searchSimSellDTO));
		statisSimSellDTO.setDeposite(simSellDao.sumDeposit(searchSimSellDTO));
		statisSimSellDTOs.add(statisSimSellDTO);
		return statisSimSellDTOs;
	}

}
