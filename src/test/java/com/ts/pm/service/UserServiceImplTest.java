package com.ts.pm.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ts.pm.dao.UserDAO;
import com.ts.pm.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {
	
	@Mock
	private UserDAO userDao;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveOrUpdateUser() {
		User user = new User(Long.valueOf(1),"Selvam","Thangaraj","1234");
		when(userDao.save(user)).thenReturn(user);
		User result = userServiceImpl.saveOrUpdateUser(user);
		assertEquals(1, result.getUserId());
		assertEquals("Selvam", result.getFirstName());
		assertEquals("Thangaraj", result.getLastName());
	}

	@Test
	public void testGetUser() {
		
		User user = new User(Long.valueOf(1),"Selvam","Thangaraj","1234");
        Optional<User> userOptional = Optional.ofNullable(user);
		when(userDao.findById(1L)).thenReturn(userOptional);
		
		User result = userServiceImpl.getUser(1);
		assertEquals(1, result.getUserId());
		assertEquals("Selvam", result.getFirstName());
		assertEquals("Thangaraj", result.getLastName());
	}

	@Test
	public void testDeleteUser() {
		User user = new User(Long.valueOf(1),"Selvam","Thangaraj","1234");
		userDao.deleteById(user.getUserId());
        //verify(userServiceImpl, times(1)).deleteUser(user.getUserId());		
		assertEquals(1,userServiceImpl.deleteUser(user.getUserId()));
	}

	@Test
	public void testGetAllUsers() {
		
		List<User> userList = new ArrayList<User>();
		userList.add(new User(Long.valueOf(1),"Selvam","Thangaraj","1234"));
		userList.add(new User(Long.valueOf(2),"Parthiban","Ramalingam","234"));
		userList.add(new User(Long.valueOf(3),"Bala","Vinayagam","34"));
		when(userDao.findAll()).thenReturn(userList);
		
		List<User> listUser = userServiceImpl.getAllUsers();
		assertEquals(3, listUser.size());
		
	}

}
