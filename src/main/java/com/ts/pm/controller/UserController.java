package com.ts.pm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ts.pm.model.User;
import com.ts.pm.service.UserService;

@RestController
@RequestMapping("/pm")
public class UserController {
	
	@Autowired
	UserService userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path = "/user" ,consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder builder) {
		LOGGER.debug("user=>"+user);
		User addedUser=userService.saveOrUpdateUser(user);
		if(null==addedUser) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
        return new ResponseEntity<User>(addedUser,headers, HttpStatus.CREATED);
		
	}
	
	@PutMapping(path = "/user" ,consumes = "application/json", produces = "application/json")
	public User updateUser(@RequestBody User user) {
		LOGGER.debug("user=>"+user);
		User addedUser=userService.saveOrUpdateUser(user);
		return addedUser;
	}
	
	@GetMapping(path = "/user/{id}" ,produces = "application/json")
	public User getUser(@PathVariable("id") Long id) {
		LOGGER.debug("id=>"+id);
		User addedUser=userService.getUser(id);
		return addedUser;
	}
	
	@GetMapping(path = "/users" ,produces = "application/json")
	public List<User> getAllUsers() {		
		List<User> listUsers=userService.getAllUsers();
		LOGGER.debug("listUsers=>"+listUsers);
		return listUsers;
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Long> deleteUser(@PathVariable("id") Long id) {
		LOGGER.debug("Delete id=>"+id);
		Long deletedUserid=userService.deleteUser(id);
		return new ResponseEntity<Long>(deletedUserid,HttpStatus.NO_CONTENT);
	}	
	

}
