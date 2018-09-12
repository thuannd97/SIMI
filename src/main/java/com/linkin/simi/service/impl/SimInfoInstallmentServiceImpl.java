package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimInfoInstallmentDao;
import com.linkin.simi.dao.SimInstallmentDao;
import com.linkin.simi.entity.InfoInstallment;
import com.linkin.simi.entity.Installment;
import com.linkin.simi.model.InfoInstallmentDTO;
import com.linkin.simi.model.SearchInfoInstallmentDTO;
import com.linkin.simi.service.SimInfoInstallmentService;
import com.linkin.simi.utils.DateTimeUtils;

@Service
@Transactional
public class SimInfoInstallmentServiceImpl implements SimInfoInstallmentService {

	@Autowired
	SimInfoInstallmentDao simInfoInstallmentDao;

	@Autowired
	SimInstallmentDao simInstallmentDao;

	@Override
	public List<InfoInstallmentDTO> find(SearchInfoInstallmentDTO searchInfoInstallmentDTO, int id) {
		List<InfoInstallmentDTO> listInfoInstallmentDTOs = new ArrayList<InfoInstallmentDTO>();
		List<InfoInstallment> listInstallments = simInfoInstallmentDao.find(searchInfoInstallmentDTO);
		
		for (InfoInstallment infoInstallment : listInstallments) {
			simInfoInstallmentDao.update(infoInstallment);
		}

		for (InfoInstallment infoInstallment : listInstallments) {
			if (infoInstallment.getInstallment().getId() == id) {
				InfoInstallmentDTO infoInstallmentDTO = new InfoInstallmentDTO();

				infoInstallmentDTO.setId(infoInstallment.getId());
				infoInstallmentDTO.setIdInstallment(infoInstallment.getInstallment().getId());
				infoInstallmentDTO.setMoney(infoInstallment.getMoney());
				infoInstallmentDTO.setMoneyReceiver(infoInstallment.getMoneyReceiver());
				infoInstallmentDTO.setTimes(infoInstallment.getTimes());
				infoInstallmentDTO
						.setDayPay(DateTimeUtils.formatDate(infoInstallment.getDayPay(), DateTimeUtils.DD_MM_YYYY));

				listInfoInstallmentDTOs.add(infoInstallmentDTO);
			}

		}
		return listInfoInstallmentDTOs;
	}

	@Override
	public List<InfoInstallmentDTO> findapi(SearchInfoInstallmentDTO searchInfoInstallmentDTO) {
		List<InfoInstallmentDTO> listInfoInstallmentDTOs = new ArrayList<InfoInstallmentDTO>();
		List<InfoInstallment> listInstallments = simInfoInstallmentDao.find(searchInfoInstallmentDTO);

		for (InfoInstallment infoInstallment : listInstallments) {
			
			InfoInstallmentDTO infoInstallmentDTO = new InfoInstallmentDTO();

			infoInstallmentDTO.setId(infoInstallment.getId());
			infoInstallmentDTO.setMoney(infoInstallment.getMoney());
			infoInstallmentDTO.setMoneyReceiver(infoInstallment.getMoneyReceiver());
			infoInstallmentDTO.setTimes(infoInstallment.getTimes());
			infoInstallmentDTO.setDayPay(DateTimeUtils.formatDate(infoInstallment.getDayPay(), DateTimeUtils.DD_MM_YYYY));

			listInfoInstallmentDTOs.add(infoInstallmentDTO);

		}
		return listInfoInstallmentDTOs;
	}

	@Override
	public long count(SearchInfoInstallmentDTO searchInfoInstallmentDTO) {
		return simInfoInstallmentDao.count(searchInfoInstallmentDTO);
	}

	@Override
	public void addInfoInstallment(InfoInstallmentDTO infoInstallmentDTO) {

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		// update
		Installment installment = simInstallmentDao.getById(infoInstallmentDTO.getIdInstallment());

		if (infoInstallmentDTO.getMoney() == installment.getInterest()) {
			installment.setInterest(0);
			simInstallmentDao.update(installment);
			installment.setLoan(installment.getLoan() - (infoInstallmentDTO.getMoney() - installment.getInterest()));
			installment.setDayStart(DateTimeUtils.parseDate(DateTimeUtils.formatDate(date, DateTimeUtils.DD_MM_YYYY), DateTimeUtils.DD_MM_YYYY));
		} 
		if(infoInstallmentDTO.getMoney() > installment.getInterest()) {
			installment.setLoan(installment.getLoan() - (infoInstallmentDTO.getMoney() - installment.getInterest()));
			installment.setInterest(0);
			installment.setDayStart(DateTimeUtils.parseDate(DateTimeUtils.formatDate(date, DateTimeUtils.DD_MM_YYYY), DateTimeUtils.DD_MM_YYYY));
			simInstallmentDao.update(installment);
		}
		if(infoInstallmentDTO.getMoney() < installment.getInterest()){
			installment.setInterest(installment.getInterest() - infoInstallmentDTO.getMoney());
			installment.setDayStart(DateTimeUtils.parseDate(DateTimeUtils.formatDate(date, DateTimeUtils.DD_MM_YYYY), DateTimeUtils.DD_MM_YYYY));
			simInstallmentDao.update(installment);
		}
		
		if(installment.getLoan()==0) {
			installment.setStatus(0);
			simInstallmentDao.update(installment);
		}
		
		// input infoInstallment table
		InfoInstallment infoInstallment = new InfoInstallment();

		infoInstallment.setInstallment(installment);
		infoInstallment.setMoney(infoInstallmentDTO.getMoney());
		infoInstallment.setMoneyReceiver(infoInstallmentDTO.getMoneyReceiver());
		infoInstallment.setTimes(infoInstallmentDTO.getTimes());
		
		
				
		infoInstallment.setDayPay(DateTimeUtils.parseDate(DateTimeUtils.formatDate(date, DateTimeUtils.DD_MM_YYYY), DateTimeUtils.DD_MM_YYYY));

		simInfoInstallmentDao.add(infoInstallment);
	}

	@Override
	public void deteleInfoInstallment(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateInfoInstallment(InfoInstallmentDTO infoInstallmentDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public InfoInstallmentDTO getInfoInstallmentById(int id) {
		InfoInstallment infoInstallment = simInfoInstallmentDao.getByIdInfostallment((long) id);
		InfoInstallmentDTO infoInstallmentDTO = new InfoInstallmentDTO();

		infoInstallmentDTO.setId(infoInstallment.getId());
		infoInstallmentDTO.setMoney(infoInstallment.getMoney());
		infoInstallmentDTO.setMoneyReceiver(infoInstallment.getMoneyReceiver());
		infoInstallmentDTO.setTimes(infoInstallment.getTimes());
		infoInstallmentDTO.setDayPay(DateTimeUtils.formatDate(infoInstallment.getDayPay(), DateTimeUtils.DD_MM_YYYY));

		return infoInstallmentDTO;
	}
}
