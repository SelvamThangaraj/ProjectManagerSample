package com.ts.pm.service;

import java.util.List;

import com.ts.pm.model.User;

public interface UserService {

	User saveOrUpdateUser(User user);
	User getUser(long userId);	
	long deleteUser(long userId);
	List<User> getAllUsers();
	List<User> sortByAttr(String attribute);
	
}
