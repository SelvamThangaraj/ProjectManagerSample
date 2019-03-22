package com.ts.pm.service;

import com.ts.pm.model.User;

public interface UserService {

	User addUser(User user);
	User getUser(long userId);
	User updateUser(User user);
	long deleteUser(long userId);
	
}
