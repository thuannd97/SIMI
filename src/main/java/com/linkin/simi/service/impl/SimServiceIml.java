package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.model.SearchSimDTO;
import com.linkin.simi.model.SimDTO;
import com.linkin.simi.service.SimService;
import com.linkin.simi.utils.DateTimeUtils;

@Service
@Transactional
public class SimServiceIml implements SimService {

	@Autowired
	private SimDao simDao;

	@Override
	public void deteleSim(Long id) {
		Sim sim = simDao.getById(id);
		if (sim != null) {
			simDao.detele(sim);
		}
	}

	@Override
	public void updateSim(SimDTO simDTO) {
		Sim sim = simDao.getById(simDTO.getId());
		if (sim != null) {
			sim.setSimNo(simDTO.getSimNo());

			simDao.update(sim);
		}
	}

	@Override
	public List<SimDTO> find(SearchSimDTO searchSimDTO) {
		List<Sim> sims = simDao.find(searchSimDTO);
		List<SimDTO> simDTOs = new ArrayList<SimDTO>();
		sims.forEach(sim -> {
			SimDTO simDTO = new SimDTO();
			simDTO.setId(sim.getId());
			simDTO.setPrice(sim.getPrice());
			simDTO.setSimNo(sim.getSimNo());
			simDTO.setStatus(sim.getStatus());
			simDTO.setCreatedDate(DateTimeUtils.formatDate(sim.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			if (sim.getUpdatedDate() != null) {
				simDTO.setUpdatedDate(DateTimeUtils.formatDate(sim.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			}

			simDTOs.add(simDTO);
		});
		return simDTOs;
	}

	@Override
	public long count(SearchSimDTO searchSimDTO) {
		return simDao.count(searchSimDTO);
	}

	@Override
	public SimDTO getSimById(Long id) {
		Sim sim = simDao.getById(id);
		if (sim != null) {
			SimDTO simDTO = new SimDTO();
			simDTO.setId(sim.getId());
			simDTO.setPrice(sim.getPrice());
			simDTO.setSimNo(sim.getSimNo());
			simDTO.setStatus(sim.getStatus());
			simDTO.setCreatedDate(DateTimeUtils.formatDate(sim.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			if (sim.getUpdatedDate() != null) {
				simDTO.setUpdatedDate(DateTimeUtils.formatDate(sim.getUpdatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			}
			return simDTO;
		}
		return null;
	}

	@Override
	public void editPrice(SimDTO simDTO) {
		Sim sim = simDao.getById(simDTO.getId());
		if (sim != null) {
			sim.setPrice(simDTO.getPrice());
			simDao.update(sim);
		}
	}

	@Override
	public void changeStatus(SimDTO simDTO) {
		// TODO Auto-generated method stub

	}


}
