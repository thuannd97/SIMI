package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.SimAd;
import com.linkin.simi.model.SearchSimAdDTO;

public interface SimAdDao {

	public void addSimAd(SimAd simAd);

	public void updateSimAd(SimAd simAd);

	public void deleteSimAd(SimAd simAd);

	public SimAd getSimAd(Long id);

	public List<SimAd> findSimAd(SearchSimAdDTO searchSimAdDTO);

	public Long countSimAd(SearchSimAdDTO searchSimAdDTO);

}
