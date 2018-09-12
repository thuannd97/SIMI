package com.linkin.simi.utils;

public enum UserRoleEnum {
	ADMIN(1, "ADMIN"), STAFF(2, "STAFF");

	private String role;
	private int id;

	UserRoleEnum(int id, String role) {
		this.id = id;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
