package com.prs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTests {

	@Autowired
	private UserRepository userRepo;

// Test get all users
	@Test
	public void testUserGetAll() {
		// Get All Users
		Iterable<User> users = userRepo.findAll();
		assertNotNull(users);
	}
// Test add update and delete
	@Before
	public void testUserAdd() {
		// Add a user
		User u = new User("usrname", "pwd", "fname", "lname", "phone", "email", true);
		assertNotNull(userRepo.save(u));
		assertEquals("lname", u.getLastName());
		u.setEmail("emailemailemail");
		assertNotNull(userRepo.save(u));
		assertEquals("emailemailemail", u.getEmail());
		userRepo.delete(u);
		assertFalse(userRepo.findById(u.getId()).isPresent());
	}

	


}
