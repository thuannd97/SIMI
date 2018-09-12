package com.linkin.simi.model;

import java.io.Serializable;
import java.util.List;

public class SimDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long price;
	private String simNo;
	private int status;
	private String createdDate;
	private String updatedDate;
	private List<SimBuyDTO> simBuyDTOs;
	private List<SimSellDTO> simSaleDTOs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SimDTO() {
		super();
	}

	public List<SimBuyDTO> getSimBuyDTOs() {
		return simBuyDTOs;
	}

	public void setSimBuyDTOs(List<SimBuyDTO> simBuyDTOs) {
		this.simBuyDTOs = simBuyDTOs;
	}

	public List<SimSellDTO> getSimSaleDTOs() {
		return simSaleDTOs;
	}

	public void setSimSaleDTOs(List<SimSellDTO> simSaleDTOs) {
		this.simSaleDTOs = simSaleDTOs;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
