package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimPayTypeDao;
import com.linkin.simi.entity.PayType;
import com.linkin.simi.model.PayTypeDTO;
import com.linkin.simi.model.SearchPayTypeDTO;
import com.linkin.simi.service.SimPayTypeService;

@Service
@Transactional
public class SimPayTypeServiceImpl implements SimPayTypeService {

	@Autowired
	SimPayTypeDao simPayTypeDao;

	@Override
	public List<PayTypeDTO> find(SearchPayTypeDTO searchPayTypeDTO) {
		List<PayTypeDTO> listPayTypeDTO=new ArrayList<PayTypeDTO>();
		List<PayType> listPayType=simPayTypeDao.find(searchPayTypeDTO);
		
		for (PayType payType : listPayType) {
			PayTypeDTO payTypeDTO=new PayTypeDTO();
			
			payTypeDTO.setId(payType.getId());
			payTypeDTO.setType(payType.getType());
			
			listPayTypeDTO.add(payTypeDTO);
		}
		return listPayTypeDTO;
	}

	@Override
	public long count(SearchPayTypeDTO searchPayTypeDTO) {
		return simPayTypeDao.count(searchPayTypeDTO);
	}

	@Override
	public void addPayType(PayTypeDTO payTypeDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detelepPayType(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePayType(PayTypeDTO payTypeDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PayTypeDTO getPayTypebyId(int id) {
		 PayType payType=simPayTypeDao.getById(id);
		 PayTypeDTO payTypeDTO =new PayTypeDTO();
		 
		 payTypeDTO.setId(payType.getId());
		 payTypeDTO.setType(payType.getType());
		 return payTypeDTO;
	}

}
