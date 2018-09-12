package com.linkin.simi.model;

public class SearchSimDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private String simNo;
	private Integer status;
			
	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public SearchSimDTO() {
		super();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
