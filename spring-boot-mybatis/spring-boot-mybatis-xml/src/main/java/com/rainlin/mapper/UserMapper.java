package com.rainlin.mapper;

import java.util.List;

import com.rainlin.model.User;

public interface UserMapper {
	
	List<User> getAll();
	
	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);

}