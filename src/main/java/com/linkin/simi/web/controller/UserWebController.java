package com.linkin.simi.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkin.simi.model.UserDTO;
import com.linkin.simi.model.UserPrincipal;
import com.linkin.simi.service.UserService;

@Controller
public class UserWebController extends BaseWebController {
	@Autowired
	private UserService accountService;


	@RequestMapping(value = "/dang-nhap")
	private String login(HttpSession httpSession, HttpServletRequest request,
			@RequestParam(required = false, name = "e") String error) {
		if (isLogin()) {
			return "redirect:/user/home";
		}

		if (error != null) {
			Exception e = (Exception) httpSession.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			request.setAttribute("msg", getLoginFailMessage(e));
		}
		return "client/user/login";
	}

	public String getLoginFailMessage(Exception e) {
		if (e instanceof UsernameNotFoundException) {
			return getMessage("user.not.found");
		}
		if (e instanceof DisabledException) {
			return getMessage("user.disabled");
		}
		if (e instanceof BadCredentialsException) {
			return getMessage("user.bad.password");
		}

		return getMessage("user.not.found");
	}

	@GetMapping(value = "/quen-mat-khau")
	private String resetPassword() {
		if (isLogin()) {
			return "redirect:/user/home";
		}
		return "client/user/reset-password";
	}

	@GetMapping(value = "/user/doi-mat-khau")
	private String changePassword(Model model) {
		model.addAttribute("userAccountDTO", new UserDTO());
		return "admin/userAccount/changePassword";
	}

	@PostMapping(value = "/user/doi-mat-khau")
	private String changePassword(Model model, @ModelAttribute(name = "userAccountDTO") UserDTO userAccountDTO,
			BindingResult bindingResult) {
		validateUserPassword(userAccountDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/changePassword";
		}
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		userAccountDTO.setId(currentUser.getId());
		try {
			accountService.changePassword(userAccountDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("password", "user.bad.password");
			return "admin/userAccount/changePassword";
		}
		return "redirect:/dang-xuat";
	}

	@GetMapping(value = "/user/profile")
	private String profile(Model model) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO accountDTO = accountService.getUserById(currentUser.getId());
		model.addAttribute("userAccountDTO", accountDTO);
		return "admin/userAccount/profile";
	}

	@PostMapping(value = "/user/profile")
	private String profile(Model model, @ModelAttribute(name = "userAccountDTO") UserDTO userAccountDTO,
			BindingResult bindingResult) {
		this.validateUpdateUser(userAccountDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/profile";
		}
		try {
			accountService.updateProfile(userAccountDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("email", "error.msg.existed.account.email");
			return "user/userAccount/profile";
		}
		return "redirect:/user/profile";
	}

	@GetMapping(value = "/user/setting")
	private String setting(Model model) {
		return "admin/userAccount/setting";
	}

	@PostMapping(value = "/user/setting")
	private String setting() {
		return "admin/userAccount/setting";
	}

	@GetMapping("/user/home")
	private String getDashboard() {
		return "redirect:/admin/index";
	}

	private void validateUserPassword(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
		if (accountDTO.getPassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getPassword())) {
			errors.rejectValue("password", "error.msg.empty.account.password");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repassword", "error.msg.empty.account.password");
		if (accountDTO.getRepassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getRepassword())) {
			errors.rejectValue("repassword", "error.msg.empty.account.password");
		}
	}

	private void validateUpdateUser(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.msg.empty.account.name");
	}
}
