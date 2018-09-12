package com.linkin.simi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonWebController extends BaseWebController {

	@RequestMapping(value = "/")
	private String home() {
		return "client/home/index";
	}
	
	@RequestMapping(value = "/access-deny")
	private String deny() {
		return "admin/error/deny";
	}
}
