package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimAdDao;
import com.linkin.simi.entity.SimAd;
import com.linkin.simi.model.SearchSimAdDTO;
import com.linkin.simi.model.SimAdDTO;
import com.linkin.simi.service.SimAdService;
import com.linkin.simi.utils.DateTimeUtils;
import com.linkin.simi.utils.SimAdStatus;

@Service
public class SimAdServiceImpl implements SimAdService {

	@Autowired
	private SimAdDao simAdDao;

	@Override
	public void addSimAd(SimAdDTO simAdDTO) {
		SimAd simAd = new SimAd();
		simAd.setSimNo(simAdDTO.getSimNo());
		simAd.setDescription(simAdDTO.getDescription());
		simAd.setPrice(simAdDTO.getPrice());
		simAd.setStatus(simAdDTO.getStatus());
		simAd.setTarget(simAdDTO.getTarget());

		simAdDao.addSimAd(simAd);
		simAdDTO.setId(simAd.getId());
	}

	@Override
	public void updateSimAd(SimAdDTO simAdDTO) {
		SimAd simAd = simAdDao.getSimAd(simAdDTO.getId());
		if (simAd != null) {
			simAd.setSimNo(simAdDTO.getSimNo());
			simAd.setDescription(simAdDTO.getDescription());
			simAd.setPrice(simAdDTO.getPrice());

			simAdDao.updateSimAd(simAd);
		}
	}

	@Override
	public void deleteSimAd(SimAdDTO simAdDTO) {
		SimAd simAd = simAdDao.getSimAd(simAdDTO.getId());
		if (simAd != null) {
			simAdDao.deleteSimAd(simAd);
		}
	}

	@Override
	public SimAdDTO getSimAd(Long id) {
		SimAd simAd = simAdDao.getSimAd(id);
		if (simAd != null) {
			SimAdDTO simAdDTO = new SimAdDTO();
			simAdDTO.setId(simAd.getId());
			simAdDTO.setSimNo(simAd.getSimNo());
			simAdDTO.setDescription(simAd.getDescription());
			simAdDTO.setPrice(simAd.getPrice());
			simAdDTO.setStatus(simAd.getStatus());
			simAdDTO.setTarget(simAd.getTarget());

			simAdDTO.setCreatedByName(simAd.getCreatedBy().getName());
			simAdDTO.setCreatedDate(DateTimeUtils.formatDate(simAd.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

			return simAdDTO;
		}
		return null;
	}

	@Override
	public List<SimAdDTO> findSimAd(SearchSimAdDTO searchSimAdDTO) {
		List<SimAd> simAds = simAdDao.findSimAd(searchSimAdDTO);
		List<SimAdDTO> simAdDTOs = new ArrayList<SimAdDTO>();

		simAds.forEach(simAd -> {
			SimAdDTO simAdDTO = new SimAdDTO();
			simAdDTO.setId(simAd.getId());
			simAdDTO.setSimNo(simAd.getSimNo());
			simAdDTO.setDescription(simAd.getDescription());
			simAdDTO.setPrice(simAd.getPrice());
			simAdDTO.setStatus(simAd.getStatus());
			simAdDTO.setTarget(simAd.getTarget());
			simAdDTO.setCreatedByName(simAd.getCreatedBy().getName());
			simAdDTO.setCreatedDate(DateTimeUtils.formatDate(simAd.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));

			simAdDTOs.add(simAdDTO);
		});

		return simAdDTOs;
	}

	@Override
	public long countSimAd(SearchSimAdDTO searchSimAdDTO) {
		return simAdDao.countSimAd(searchSimAdDTO);
	}

	@Override
	public void changeStatus(SimAdDTO simAdDTO) {
		SimAd simAd = simAdDao.getSimAd(simAdDTO.getId());
		if (simAd != null) {
			if (simAd.getStatus() == SimAdStatus.POSTED || simAd.getStatus() == SimAdStatus.NOTPOST) {
				simAd.setStatus(simAdDTO.getStatus());
				simAdDao.updateSimAd(simAd);
			}
		}
	}

}
