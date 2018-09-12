package com.linkin.simi.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.linkin.simi.utils.FormatUtils;

public class UserPrincipal extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long balance;

	public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	public String getBalanceStr() {
		if (this.balance != null) {
			return FormatUtils.formatVNDCurrency(this.balance);
		}
		return FormatUtils.formatVNDCurrency(0L);
	}
}