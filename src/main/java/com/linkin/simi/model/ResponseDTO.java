package com.linkin.simi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO<T> {
	@JsonProperty("recordsTotal")
	private long totalRecords;
	private long recordsFiltered;
	private List<T> data;

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

}
