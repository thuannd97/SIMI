package com.linkin.simi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.UserDTO;
import com.linkin.simi.model.UserPrincipal;
import com.linkin.simi.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserAPIController {

	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ResponseDTO<UserDTO>> findUser(@RequestBody SearchUserDTO searchUserDTO) {
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(userService.findUsers(searchUserDTO));
		return new ResponseEntity<ResponseDTO<UserDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/user/account/me")
	public ResponseEntity<UserDTO> me() {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO userAccountDTO = userService.getUserById(currentUser.getId());
		if (userAccountDTO == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserDTO>(userAccountDTO, HttpStatus.OK);
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<UserDTO> getAccountByID(@PathVariable(name = "id") Long id) {
		UserDTO userAccountDTO = userService.getUserById(id);
		if (userAccountDTO == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserDTO>(userAccountDTO, HttpStatus.OK);
	}

	@PutMapping("/account/update")
	public UserDTO updateUser(@RequestBody UserDTO userAccountDTO) {
		userService.updateUser(userAccountDTO);
		return userAccountDTO;
	}

	@PostMapping("/account/reset-password")
	public UserDTO resetPassword(@RequestBody UserDTO userAccountDTO) {
		userService.resetPassword(userAccountDTO);
		return userAccountDTO;
	}

	@PostMapping("/user/account/change-password")
	public UserDTO changePassword(@RequestBody UserDTO userAccountDTO) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		userAccountDTO.setId(currentUser.getId());
		userService.changePassword(userAccountDTO);
		return userAccountDTO;
	}

	@PostMapping("/account/add")
	public UserDTO addUser(@RequestBody UserDTO userAccountDTO) {
		userService.addUser(userAccountDTO);
		return userAccountDTO;
	}
}
