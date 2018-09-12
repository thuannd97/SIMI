package com.linkin.simi.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.UserDTO;
import com.linkin.simi.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class UserAdminController {

	@Autowired
	private UserService userService;

	@GetMapping("/accounts")
	public String listUser() {
		return "admin/userAccount/listUser";
	}

	@PostMapping(value = "/accounts")
	public ResponseEntity<com.linkin.simi.model.ResponseDTO<UserDTO>> findUserAccount(
			@RequestBody SearchUserDTO searchUserAccountDTO) {
		com.linkin.simi.model.ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.findUsers(searchUserAccountDTO));
		long total = userService.countUsers(searchUserAccountDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);
		return new ResponseEntity<ResponseDTO<UserDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/account/add")
	public String addUser(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userAccountDTO", userDTO);
		return "admin/userAccount/addUser";
	}

	@PostMapping("/account/add")
	public String addUser(@ModelAttribute(name = "userAccountDTO") UserDTO userAccountDTO,
			BindingResult bindingResult) {

		this.validateAddUser(userAccountDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/addUser";
		}
		try {
			userService.addUser(userAccountDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("username", "error.msg.existed.account.phone");
			return "admin/userAccount/addUser";
		}

		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/update/{id}")
	public String updateUser(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userAccountDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userAccountDTO);
		return "admin/userAccount/updateUser";
	}

	@PostMapping("/account/update")
	public String updateUser(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO,
			BindingResult bindingResult) {
		this.validateUpdateUser(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/updateUser";
		}
		try {
			userService.updateUser(userDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("username", "error.msg.existed.account.phone");
			return "admin/userAccount/updateUser";
		}
		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/change-lock/{id}")
	public ResponseEntity<String> changeLockedUserStatus(@PathVariable(name = "id") Long id) {
		userService.changeAccountLock(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/reset-password/{id}")
	public String resetPassword(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userAccountDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userAccountDTO);
		return "admin/userAccount/resetPassword";
	}

	@PostMapping("/account/reset-password")
	public String resetPassword(@ModelAttribute(name = "userAccountDTO") UserDTO userAccountDTO,
			BindingResult bindingResult) {
		validateUserPassword(userAccountDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/resetPassword";
		}
		userService.setupUserPassword(userAccountDTO);
		return "redirect:/admin/accounts";
	}

	private void validateAddUser(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.msg.empty.account.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
		if (!accountDTO.getPhone().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("phone", "error.msg.invalid.phone");
		}
		if (accountDTO.getPassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getPassword())) {
			errors.rejectValue("password", "error.msg.empty.account.password");
		}
	}

	private void validateUpdateUser(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.msg.empty.account.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
		if (!accountDTO.getPhone().matches("[0]{1}[0-9]{9,10}")) {
			errors.rejectValue("username", "error.msg.invalid.phone");
		}
	}

	private void validateUserPassword(Object object, Errors errors) {
		UserDTO accountDTO = (UserDTO) object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
		if (accountDTO.getPassword().length() < 6 && StringUtils.isNotBlank(accountDTO.getPassword())) {
			errors.rejectValue("password", "error.msg.empty.account.password");
		}
	}

}
