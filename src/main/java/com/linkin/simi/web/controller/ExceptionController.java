package com.linkin.simi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.linkin.thecao.web.controller")
public class ExceptionController {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected String handleConflict(Exception ex, Model model) {
		logger.info("Conflict: " + ex);
		model.addAttribute("msg", "");
		return "admin/error/error";
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public String handleAccessDeniedException(Exception ex, Model model) {
		logger.info("Deny: " + ex);
		model.addAttribute("msg", "");
		return "admin/error/deny";
	}

	@ExceptionHandler({ Exception.class })
	public String handleInternalException(Exception ex, Model model) {
		logger.error("exception: " + ex);
		model.addAttribute("msg", "");
		return "admin/error/error";
	}
}
