package com.linkin.simi.utils;

public enum SimStatus {
	AVAILABLE(0), SOLD(1), LOST(2);

	private int status;

	private SimStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
