package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.UserDTO;

public interface UserService {
	public void addUser(UserDTO UserDTO);

	public void updateUser(UserDTO accountDTO);

	public void updateProfile(UserDTO UserDTO);
	
	public void deleteUser(Long id);
	
	public List<UserDTO> findUsers(SearchUserDTO searchUserDTO);

	public UserDTO getUserById(Long id);

	public void changeAccountLock(long id);

	public long countUsers(SearchUserDTO searchUserDTO);

	public void changePassword(UserDTO accountDTO);

	public void resetPassword(UserDTO accountDTO);
	
	public void setupUserPassword(UserDTO accountDTO);
	
}
