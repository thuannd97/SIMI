package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.PayType;
import com.linkin.simi.model.SearchPayTypeDTO;

public interface SimPayTypeDao  {
	public void add (PayType payType);

	public void detele(PayType payType);

	public void update(PayType payType);

	public PayType getById(int id);

	List<PayType> find(SearchPayTypeDTO searchPayTypeDTO);

	long count(SearchPayTypeDTO searchPayTypeDTO);

}
