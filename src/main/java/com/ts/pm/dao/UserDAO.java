package com.ts.pm.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ts.pm.model.User;
@Repository
public interface UserDAO extends CrudRepository<User,Long>{
	
	List<User> findByFirstNameAndLastNameAndEmployeeId(String firstName, String lastName,String employeeId);

}
