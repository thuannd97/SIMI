package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimAdDTO;
import com.linkin.simi.model.SimAdDTO;

public interface SimAdService {

	public void addSimAd(SimAdDTO simAdDTO);

	public void updateSimAd(SimAdDTO simAdDTO);

	public void deleteSimAd(SimAdDTO simAdDTO);

	public SimAdDTO getSimAd(Long id);

	public List<SimAdDTO> findSimAd(SearchSimAdDTO searchSimAdDTO);

	public long countSimAd(SearchSimAdDTO searchSimAdDTO);

	public void changeStatus(SimAdDTO simAdDTO);
}
