package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimDao;
import com.linkin.simi.dao.SimSellDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.entity.SimSell;
import com.linkin.simi.entity.User;
import com.linkin.simi.exception.NotAvailableSimException;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.model.SimSellDTO;
import com.linkin.simi.service.SimSellService;
import com.linkin.simi.utils.DateTimeUtils;
import com.linkin.simi.utils.SimStatus;

@Service
public class SimSellServiceIml implements SimSellService {

	@Autowired
	SimSellDao simSellDao;

	@Autowired
	SimDao simDao;

	@Override
	public void addSimSell(SimSellDTO simSellDTO) {
		Sim sim = simDao.getByPhone(simSellDTO.getSimNo());
		if (sim != null && sim.getStatus() == SimStatus.AVAILABLE.getStatus()) {
			sim.setStatus(SimStatus.SOLD.getStatus());

			SimSell simSell = new SimSell();
			simSell.setSim(sim);
			simSell.setSellPrice(simSellDTO.getSellPrice());
			simSell.setDeposit(simSellDTO.getDeposit());
			simSell.setSellDate(DateTimeUtils.parseDate(simSellDTO.getSellDate(), DateTimeUtils.DD_MM_YYYY));
			simSell.setSeller(new User(simSellDTO.getSellerId()));
			simSell.setCustomerName(simSellDTO.getCustomerName());
			simSell.setCustomerInfo(simSellDTO.getCustomerInfo());

			sim.getSimSells().add(simSell);

			simDao.update(sim);

			simSellDTO.setId(simSell.getId());
		} else {
			throw new NotAvailableSimException();
		}
	}

	@Override
	public void deteleSimSell(Long id) {
		SimSell simSell = simSellDao.getSimSaleById(id);
		if (simSell != null) {
			Sim sim = simSell.getSim();
			sim.setStatus(SimStatus.AVAILABLE.getStatus());

			sim.getSimSells().remove(simSell);

			simDao.update(sim);
		}
	}

	@Override
	public void updateSimSell(SimSellDTO simSellDTO) {
		SimSell simSell = simSellDao.getSimSaleById(simSellDTO.getId());
		simSell.setSellPrice(simSellDTO.getSellPrice());
		simSell.setDeposit(simSellDTO.getDeposit());
		simSell.setSellDate(DateTimeUtils.parseDate(simSellDTO.getSellDate(), DateTimeUtils.DD_MM_YYYY));
		simSell.setSeller(new User(simSellDTO.getSellerId()));
		simSell.setCustomerName(simSellDTO.getCustomerName());
		simSell.setCustomerInfo(simSellDTO.getCustomerInfo());
		simSellDao.updateSimSell(simSell);
	}

	@Override
	public List<SimSellDTO> find(SearchSimSellDTO searchSimSellDTO) {
		List<SimSell> simSells = simSellDao.find(searchSimSellDTO);
		List<SimSellDTO> simSelldtos = new ArrayList<SimSellDTO>();

		for (SimSell simSell : simSells) {
			SimSellDTO saleDTO = new SimSellDTO();
			saleDTO.setId(simSell.getId());
			saleDTO.setSimNo(simSell.getSim().getSimNo());
			saleDTO.setSellPrice(simSell.getSellPrice());
			saleDTO.setSellDate(DateTimeUtils.formatDate(simSell.getSellDate(), DateTimeUtils.DD_MM_YYYY));
			saleDTO.setSellerName(simSell.getSeller().getName());
			saleDTO.setSellerId(simSell.getSeller().getId());
			saleDTO.setUpdatedDate(DateTimeUtils.formatDate(simSell.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY));
			saleDTO.setUpdatedBy(simSell.getUpdatedBy().getName());
			saleDTO.setDeposit(simSell.getDeposit());
			saleDTO.setCustomerName(simSell.getCustomerName());
			saleDTO.setCustomerInfo(simSell.getCustomerInfo());
			saleDTO.setCreatedDate(DateTimeUtils.formatDate(simSell.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			saleDTO.setCreatedBy(simSell.getCreatedBy().getName());

			simSelldtos.add(saleDTO);
		}

		return simSelldtos;
	}

	@Override
	public long count(SearchSimSellDTO searchSimSellDTO) {
		return simSellDao.count(searchSimSellDTO);
	}

	@Override
	public SimSellDTO get(Long id) {
		SimSell simSell = simSellDao.getSimSaleById(id);
		if (simSell != null) {
			SimSellDTO simSellDTO = new SimSellDTO();
			simSellDTO.setId(simSell.getId());
			simSellDTO.setSimNo(simSell.getSim().getSimNo());
			simSellDTO.setSellPrice(simSell.getSellPrice());
			simSellDTO.setSellDate(DateTimeUtils.formatDate(simSell.getSellDate(), DateTimeUtils.DD_MM_YYYY));
			simSellDTO.setSellerName(simSell.getSeller().getName());
			simSellDTO.setSellerId(simSell.getSeller().getId());

			simSellDTO.setDeposit(simSell.getDeposit());
			simSellDTO.setCustomerName(simSell.getCustomerName());
			simSellDTO.setCustomerInfo(simSell.getCustomerInfo());

			simSellDTO
					.setCreatedDate(DateTimeUtils.formatDate(simSell.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			simSellDTO.setCreatedBy(simSell.getCreatedBy().getName());

			simSellDTO.setUpdatedBy(simSell.getUpdatedBy().getName());
			simSellDTO
					.setUpdatedDate(DateTimeUtils.formatDate(simSell.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));

			return simSellDTO;
		}

		return null;
	}

}
