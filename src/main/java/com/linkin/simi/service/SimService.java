package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchSimDTO;
import com.linkin.simi.model.SimDTO;

public interface SimService {
	public List<SimDTO> find(SearchSimDTO searchSimDTO);
	
	public long count(SearchSimDTO searchSimDTO);

	public void deteleSim(Long id);

	public void updateSim(SimDTO simDTO);
	
	public void changeStatus(SimDTO simDTO);
	
	public SimDTO getSimById(Long id);
	
	public void editPrice(SimDTO simDTO);
}
