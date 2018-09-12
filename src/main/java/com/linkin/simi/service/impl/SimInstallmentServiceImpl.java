package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.SimInstallmentDao;
import com.linkin.simi.dao.SimPayTypeDao;
import com.linkin.simi.dao.SimSellDao;
import com.linkin.simi.entity.Installment;
import com.linkin.simi.model.InstallmentDTO;
import com.linkin.simi.model.SearchInstallmentDTO;
import com.linkin.simi.service.SimInstallmentService;
import com.linkin.simi.utils.DateTimeUtils;

@Service
@Transactional
public class SimInstallmentServiceImpl implements SimInstallmentService {

	@Autowired
	SimInstallmentDao simInstallmentDao;

	@Autowired
	SimSellDao simSellDao;

	@Autowired
	SimPayTypeDao simPayTypeDao;

	@Override
	public List<InstallmentDTO> find(SearchInstallmentDTO searchInstallmentDTO) {
		List<Installment> list = simInstallmentDao.find(searchInstallmentDTO);
		List<InstallmentDTO> listdto = new ArrayList<InstallmentDTO>();

		for (Installment installment : list) {
			InstallmentDTO dto = new InstallmentDTO();

			dto.setId(installment.getId());
			dto.setSimNo(installment.getSimNo());
			dto.setCustomerName(installment.getCustomerName());
			dto.setCustomerPhone(installment.getCustomerPhone());
			dto.setLoan(installment.getLoan());
			dto.setInterest(caculatedInterest(installment.getDayStart(), installment.getPayType().getType(), installment.getLoan()));
			
			//cập nhật lại tiền lãi trong  my sql 
			installment.setInterest(caculatedInterest(installment.getDayStart(), installment.getPayType().getType(), installment.getLoan()));
			simInstallmentDao.update(installment);
			
			dto.setDayStart(DateTimeUtils.formatDate(installment.getDayStart(), DateTimeUtils.DD_MM_YYYY));
			dto.setPayTypeId(installment.getPayType().getId());
			dto.setStatus(installment.getStatus());
			listdto.add(dto);
		}
		return listdto;
	}

	@Override
	public long count(SearchInstallmentDTO searchInstallmentDTO) {
		return simInstallmentDao.count(searchInstallmentDTO);
	}

	@Override
	public void addInstallment(InstallmentDTO installmentDTO) {
		Installment installment = new Installment();

		installment.setSimNo(installmentDTO.getSimNo());
		installment.setCustomerName(installmentDTO.getCustomerName());
		installment.setCustomerPhone(installmentDTO.getCustomerPhone());
		installment.setLoan(installmentDTO.getLoan());
		installment.setInterest(
				caculatedInterest(DateTimeUtils.parseDate(installmentDTO.getDayStart(), DateTimeUtils.DD_MM_YYYY),
						simPayTypeDao.getById(installmentDTO.getPayTypeId()).getType(), installmentDTO.getLoan()));
		installment.setDayStart(DateTimeUtils.parseDate(installmentDTO.getDayStart(), DateTimeUtils.DD_MM_YYYY));
		installment.setPayType(simPayTypeDao.getById(installmentDTO.getPayTypeId()));
		installment.setStatus(1);
		
		simInstallmentDao.add(installment);
	}

	@Override
	public void deteleInstallment(Long id) {
		simInstallmentDao.detele(simInstallmentDao.getById(id));
	}

	@Override
	public void updateInstallment(InstallmentDTO installmentDTO) {

		Installment installment = new Installment();

		installment.setId(installmentDTO.getId());
		installment.setSimNo(installmentDTO.getSimNo());
		installment.setCustomerName(installmentDTO.getCustomerName());
		installment.setCustomerPhone(installmentDTO.getCustomerPhone());
		installment.setLoan(installmentDTO.getLoan());
		installment.setInterest(installmentDTO.getInterest());
		installment.setDayStart(DateTimeUtils.parseDate(installmentDTO.getDayStart(), DateTimeUtils.DD_MM_YYYY));

		simInstallmentDao.update(installment);

	}

	@Override
	public InstallmentDTO getInstallmentById(Long id) {
		InstallmentDTO installmentDTO = new InstallmentDTO();
		Installment installment = simInstallmentDao.getById(id);

		installmentDTO.setId(installment.getId());
		installmentDTO.setSimNo(installment.getSimNo());
		installmentDTO.setCustomerName(installment.getCustomerName());
		installmentDTO.setCustomerPhone(installment.getCustomerPhone());
		installmentDTO.setLoan(installment.getLoan());
		installmentDTO.setInterest(installment.getInterest());
		installmentDTO.setDayStart(DateTimeUtils.formatDate(installment.getDayStart(), DateTimeUtils.DD_MM_YYYY));
		return installmentDTO;
	}

	public double caculatedInterest(Date daystart, double typepay, double loan) {

		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Calendar calendar3 = Calendar.getInstance();

		calendar2.setTime(daystart);
		calendar.setTime(daystart);
		calendar.add(calendar.MONTH, 1);
		//phần trăm theo tháng
		if (typepay<1.0 && typepay>0.0) {
			// so ngay cua thang do
			long ngay = ((calendar.getTime().getTime() - calendar2.getTime().getTime()) / (1000 * 60 * 60 * 24));
			
			// tong so ngay da no
			long tongNgayDaNo = (calendar3.getTime().getTime() - calendar2.getTime().getTime()) / (1000 * 60 * 60 * 24);
			
			double tongNgayDaNoD=(double) tongNgayDaNo;
			double ngayD = (double) ngay;
			
			//tính phần trăm mỗi ngày theo tháng 30 hoặc 31 hoặc 28 ngày
			double laingay = typepay/ ngayD;
			
			//Số tiền lãi đến ngày hiện tại tính theo phần trăm tháng
			double interest = ((laingay * loan) * tongNgayDaNo);
			
			return (long) interest;
		}
		if(typepay>100000) {
			
			// tong so ngay da no
			long tongNgayDaNo = (calendar3.getTime().getTime() - calendar2.getTime().getTime()) / (1000 * 60 * 60 * 24);
			// so ngay cua thang do
			long ngay = ((calendar.getTime().getTime() - calendar2.getTime().getTime()) / (1000 * 60 * 60 * 24));
			
			
			double ngayD = (double) ngay;
			double tongNgayDaNoD=(double) tongNgayDaNo;
			
			double interest = (tongNgayDaNo * (typepay/ngayD)) ;
			return (long) interest;
		}
		//phần trăm theo ngày
		
		else {
			// tong so ngay da no
			long tongNgayDaNo = (calendar3.getTime().getTime() - calendar2.getTime().getTime()) / (1000 * 60 * 60 * 24);
			double interest = (tongNgayDaNo * typepay) ;
			return (long) interest;
		}
	}
}
