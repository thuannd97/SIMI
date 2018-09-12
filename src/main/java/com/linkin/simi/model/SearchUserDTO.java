package com.linkin.simi.model;

public class SearchUserDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;
	private Integer roleId;
	private Boolean enabled;

	public SearchUserDTO() {
		super();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
