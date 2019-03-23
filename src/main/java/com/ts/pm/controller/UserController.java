package com.ts.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.pm.model.User;
import com.ts.pm.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping(path = "/add" ,consumes = "application/json", produces = "application/json")
	public User addUser(@RequestBody User user) {
		System.out.println("user=>"+user);
		User addedUser=userService.saveOrUpdateUser(user);
		return addedUser;
	}
	
	@PutMapping(path = "/update" ,consumes = "application/json", produces = "application/json")
	public User updateUser(@RequestBody User user) {
		System.out.println("user=>"+user);
		User addedUser=userService.saveOrUpdateUser(user);
		return addedUser;
	}
	
	@GetMapping(path = "/{id}" ,produces = "application/json")
	public User getUser(@PathVariable("id") Long id) {
		System.out.println("id=>"+id);
		User addedUser=userService.getUser(id);
		return addedUser;
	}
	
	@GetMapping(path = "/getAll" ,produces = "application/json")
	public List<User> getAllUsers() {		
		List<User> listUsers=userService.getAllUsers();
		System.out.println("listUsers=>"+listUsers);
		return listUsers;
	}
	

}
