package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.Sim;
import com.linkin.simi.model.SearchSimDTO;

public interface SimDao {
	public void add(Sim sim);

	public void detele(Sim sim);

	public void update(Sim sim);

	public Sim getById(Long id);

	List<Sim> find(SearchSimDTO searchSimDTO);

	long count(SearchSimDTO searchSimDTO);

	Sim getByPhone(String phone);
	
	
}
