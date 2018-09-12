package com.linkin.simi.model;

public class SearchInstallmentDTO extends SearchDTO {
	private static final long serialVersionUID = 1L;
	
	private String simNo;

	
	public SearchInstallmentDTO() {
		super();
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
}
