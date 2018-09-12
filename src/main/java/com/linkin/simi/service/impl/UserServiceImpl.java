package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.UserDao;
import com.linkin.simi.entity.Role;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchUserDTO;
import com.linkin.simi.model.UserDTO;
import com.linkin.simi.model.UserPrincipal;
import com.linkin.simi.service.UserService;
import com.linkin.simi.utils.DateTimeUtils;
import com.linkin.simi.utils.PasswordGenerator;
import com.linkin.simi.utils.UserRoleEnum;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public void addUser(UserDTO userDTO) {
		// add to account
		User userAccount = new User();
		userAccount.setName(userDTO.getName());
		userAccount.setPhone(userDTO.getPhone());
		userAccount.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		userAccount.setEnabled(true);
		userAccount.setRole(new Role(UserRoleEnum.STAFF.getId()));
		userDao.add(userAccount);

		userDTO.setId(userAccount.getId());
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		User user = userDao.getById(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());
			userDao.update(user);
			expireUserSessions(user.getPhone());
		}
	}

	@Override
	public void updateProfile(UserDTO userDTO) {
		User user = userDao.getById(userDTO.getId());
		if (user != null) {
			user.setName(userDTO.getName());
			userDao.update(user);
			updateUserSessions(user.getPhone());
		}
	}

	@Override
	public void deleteUser(Long id) {
		User user = userDao.getById(id);
		if (user != null) {
			userDao.delete(user);
			expireUserSessions(user.getPhone());
		}
	}

	@Override
	public List<UserDTO> findUsers(SearchUserDTO searchUserDTO) {
		List<User> userAccounts = userDao.find(searchUserDTO);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();

		userAccounts.forEach(user -> {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setName(user.getName());
			// test
			userDTO.setPassword(user.getPassword());
			userDTO.setPhone(user.getPhone());
			userDTO.setRoleId(user.getRole().getId());
			userDTO.setRoleName(user.getRole().getName());
			userDTO.setEnabled(user.getEnabled());
			userDTO.setCreatedDate(DateTimeUtils.formatDate(user.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			userDTOs.add(userDTO);
		});

		return userDTOs;
	}

	@Override
	public long countUsers(SearchUserDTO searchUserDTO) {
		return userDao.count(searchUserDTO);
	}

	@Override
	public UserDTO getUserById(Long id) {
		User userAccount = userDao.getById(id);
		if (userAccount != null) {
			UserDTO userAccountDTO = new UserDTO();
			userAccountDTO.setId(userAccount.getId());
			userAccountDTO.setName(userAccount.getName());
			userAccountDTO.setPhone(userAccount.getPhone());
			userAccountDTO.setRoleId(userAccount.getRole().getId());
			userAccountDTO.setRoleName(userAccount.getRole().getName());
			userAccountDTO.setEnabled(userAccount.getEnabled());
			userAccountDTO.setCreatedDate(
					DateTimeUtils.formatDate(userAccount.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
			return userAccountDTO;
		}
		return null;
	}

	@Override
	public void changeAccountLock(long id) {
		User userAccount = userDao.getById(id);
		if (userAccount != null) {
			userAccount.setEnabled(!userAccount.getEnabled());
			userDao.update(userAccount);

			expireUserSessions(userAccount.getPhone());
		}
	}

	private void expireUserSessions(String username) {
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			if (principal instanceof User) {
				UserPrincipal userDetails = (UserPrincipal) principal;
				if (userDetails.getUsername().equals(username)) {
					for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
						information.expireNow();
					}
				}
			}
		}
	}

	private void updateUserSessions(String username) {
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			if (principal instanceof User) {
				UserPrincipal userDetails = (UserPrincipal) principal;
				if (userDetails.getUsername().equals(username)) {
					UserPrincipal details = (UserPrincipal) loadUserByUsername(username);
					// userDetails.setBalance(details.getBalance());
					userDetails.setName(details.getName());
				}
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByPhone(username);

		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

		UserPrincipal accountDTO = new UserPrincipal(user.getPhone(), user.getPassword(), user.getEnabled(), true, true,
				true, authorities);
		accountDTO.setId(user.getId());
		accountDTO.setName(user.getName());

		return accountDTO;
	}

	@Override
	public void changePassword(UserDTO userDTO) {
		User user = userDao.getById(userDTO.getId());
		if (user != null && PasswordGenerator.checkHashStrings(userDTO.getPassword(), user.getPassword())) {
			user.setPassword(PasswordGenerator.getHashString(userDTO.getRepassword()));
			userDao.update(user);

			expireUserSessions(user.getPhone());
		} else {
			throw new DataIntegrityViolationException("wrong password");
		}
	}

	@Override
	public void resetPassword(UserDTO accountDTO) {
		User user = userDao.getByPhone(accountDTO.getPhone());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(PasswordGenerator.generateRandomPassword()));
			userDao.update(user);

			expireUserSessions(user.getPhone());
		}
	}

	@Override
	public void setupUserPassword(UserDTO accountDTO) {
		User user = userDao.getById(accountDTO.getId());
		if (user != null) {
			user.setPassword(PasswordGenerator.getHashString(accountDTO.getPassword()));
			userDao.update(user);

			expireUserSessions(user.getPhone());
		}
	}

}
