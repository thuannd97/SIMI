package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchUserDTO;

public interface UserDao {
	
	void add(User User);

	void update(User User);

	void delete(User User);

	List<User> find(SearchUserDTO searchUserDTO);

	long count(SearchUserDTO searchUserDTO);

	User getById(Long id);

	User getByPhone(String phone);
	
}
