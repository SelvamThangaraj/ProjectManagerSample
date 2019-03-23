package com.ts.pm.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ts.pm.model.User;

public interface UserDAO extends CrudRepository<User,Long>{
	
	List<User> findByFirstNameAndLastNameAndEmployeeId(String firstName, String lastName,String employeeId);

}
