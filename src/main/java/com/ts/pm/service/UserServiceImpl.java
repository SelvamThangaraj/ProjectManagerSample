package com.ts.pm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.UserDAO;
import com.ts.pm.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;
	
	@Override	
	public User saveOrUpdateUser(User user) {
		
		User savedUser=userDao.save(user);
		return savedUser;
	}

	@Override
	public User getUser(long userId) {
		User user=userDao.findById(userId).orElse(null);
		return user;
	}
	
	@Override
	public long deleteUser(long userId) {
		userDao.deleteById(userId);
		return userId;
	}

	@Override
	public List<User> getAllUsers() {
		Iterable<User> iterableUsers=userDao.findAll();
		List<User> userList=new ArrayList<User>();
		iterableUsers.spliterator().forEachRemaining(user->userList.add(user));
		return userList;
	}

}
