package com.ts.pm.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.pm.dao.UserDAO;
import com.ts.pm.model.User;

@Service
public class UserServiceImpl implements UserService {

	private final Logger LOGGER=LoggerFactory.getLogger(this.getClass());
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

	@Override
	public List<User> sortByAttr(String attribute) {
		LOGGER.debug("Service sortByAttr=>"+attribute);
		List<User> userList=getAllUsers();	
		LOGGER.debug("Service sortByAttr userCount=>"+userList.size());
		if("firstName".equalsIgnoreCase(attribute)) {			
			userList.sort((User u1,User u2)->u1.getFirstName().compareTo(u2.getFirstName()));
		}else if("lastName".equalsIgnoreCase(attribute)) {
			userList.sort((User u1,User u2)->u1.getLastName().compareTo(u2.getLastName()));
		}else if("employeeId".equalsIgnoreCase(attribute)) {
			userList.sort((User u1,User u2)->u1.getEmployeeId().compareTo(u2.getEmployeeId()));
		}	
		LOGGER.debug("Service sortByAttr sorted list=>"+userList.toString());
		return userList;
	}

}
