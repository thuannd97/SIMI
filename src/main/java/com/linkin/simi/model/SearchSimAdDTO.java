package com.linkin.simi.model;

public class SearchSimAdDTO extends SearchDTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String simNo;
	private Long price;
	private Integer status;
	private Integer target;
	
	public SearchSimAdDTO() {
		super();
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

}
