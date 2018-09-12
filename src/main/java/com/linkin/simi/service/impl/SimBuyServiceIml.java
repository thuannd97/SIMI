package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimBuyDao;
import com.linkin.simi.dao.SimDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.entity.SimBuy;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.model.SimBuyDTO;
import com.linkin.simi.service.SimBuyService;
import com.linkin.simi.utils.DateTimeUtils;
import com.linkin.simi.utils.SimStatus;

@Service
@Transactional
public class SimBuyServiceIml implements SimBuyService {

	@Autowired
	SimBuyDao simBuyDao;

	@Autowired
	SimDao simDao;

	@Override
	public void addSimBuy(SimBuyDTO simBuyDTO) {
		Sim sim = simDao.getByPhone(simBuyDTO.getSimNo());
		if (sim != null) {
			SimBuy simBuy = new SimBuy();
			simBuy.setSim(sim);
			simBuy.setBuyDate(DateTimeUtils.parseDate(simBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
			simBuy.setBuyer(new User(simBuyDTO.getBuyerId()));
			simBuy.setCost(simBuyDTO.getCost());

			sim.getSimBuys().add(simBuy);
			sim.setStatus(SimStatus.AVAILABLE.getStatus());
			simDao.update(sim);
		}

		if (sim == null) {
			sim = new Sim();
			sim.setPrice(0L);
			sim.setSimNo(simBuyDTO.getSimNo());
			sim.setStatus(SimStatus.AVAILABLE.getStatus());

			List<SimBuy> simBuys = new ArrayList<SimBuy>();
			SimBuy simBuy = new SimBuy();

			simBuy.setSim(sim);
			simBuy.setBuyDate(DateTimeUtils.parseDate(simBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
			simBuy.setBuyer(new User(simBuyDTO.getBuyerId()));
			simBuy.setCost(simBuyDTO.getCost());

			simBuys.add(simBuy);

			sim.setSimBuys(simBuys);

			simDao.add(sim);
		}
	}

	@Override
	public void deteleSimBuy(Long id) {
		SimBuy simBuy = simBuyDao.getSimBuyById(id);
		if (simBuy != null) {
			simBuyDao.deteleSimBuy(simBuy);
		}
	}

	@Override
	public void updateSimBuy(SimBuyDTO simBuyDTO) {
		SimBuy simBuy = simBuyDao.getSimBuyById(simBuyDTO.getId());
		if (simBuy != null) {
			simBuy.setBuyDate(DateTimeUtils.parseDate(simBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
			simBuy.setBuyer(new User(simBuyDTO.getBuyerId()));
			simBuy.setCost(simBuyDTO.getCost());

			simBuyDao.updateSimBuy(simBuy);
		}
	}

	@Override
	public long count(SearchSimBuyDTO searchSimBuyDTO) {
		return simBuyDao.count(searchSimBuyDTO);
	}

	@Override
	public List<SimBuyDTO> find(SearchSimBuyDTO searchSimBuyDTO) {
		List<SimBuy> simBuys = simBuyDao.find(searchSimBuyDTO);
		List<SimBuyDTO> simBuyDTOs = new ArrayList<SimBuyDTO>();

		for (SimBuy simBuy : simBuys) {
			SimBuyDTO simBuyDTO = new SimBuyDTO();
			simBuyDTO.setId(simBuy.getId());
			simBuyDTO.setSimNo(simBuy.getSim().getSimNo());
			simBuyDTO.setCost(simBuy.getCost());
			simBuyDTO.setCreatedByName(simBuy.getCreatedBy().getName());
			simBuyDTO.setCreatedDate(DateTimeUtils.formatDate(simBuy.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			simBuyDTO.setBuyDate(DateTimeUtils.formatDate(simBuy.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
			simBuyDTO.setBuyerName(simBuy.getBuyer().getName());
			simBuyDTO.setBuyerId(simBuy.getBuyer().getId());
			
			simBuyDTOs.add(simBuyDTO);
		}

		return simBuyDTOs;
	}

	@Override
	public SimBuyDTO getSimBuyById(Long id) {
		SimBuyDTO simBuyDTO = new SimBuyDTO();
		SimBuy simBuy = simBuyDao.getSimBuyById(id);
		simBuyDTO.setId(simBuy.getId());
		simBuyDTO.setSimNo(simBuy.getSim().getSimNo());
		simBuyDTO.setCost(simBuy.getCost());
		simBuyDTO.setCreatedByName(simBuy.getCreatedBy().getName());
		simBuyDTO.setBuyDate(DateTimeUtils.formatDate(simBuy.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
		simBuyDTO.setBuyerName(simBuy.getBuyer().getName());

		return simBuyDTO;
	}

}
