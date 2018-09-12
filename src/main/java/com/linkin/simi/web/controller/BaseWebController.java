package com.linkin.simi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseWebController {
	@Autowired
	private MessageSource messageSource;

	protected String getMessage(String key) {
		return messageSource.getMessage(key, null, null);
	}

	protected String getMessage(String key, Object[] values) {
		return messageSource.getMessage(key, values, null);
	}

	protected boolean isLogin() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
}
