package com.prs;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.User;
import com.prs.business.PurchaseRequest;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestTests {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	@Autowired
	private UserRepository userRepo;
	@Test
	public void testPurchaseRequestGetAll() {
		// Get All PurchaseRequests
		Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepo.findAll();
		assertNotNull(purchaseRequests);
	}
	// Test add update and delete
	@Before
	public void testPurchaseRequestAdd() {
		// Add a user to pass into purchaseRequest
		Iterable<User> users = userRepo.findAll();
		User u = users.iterator().next();
// Add a purchaseRequest
		PurchaseRequest v = new PurchaseRequest(u, "PR Description", "justimafication",LocalDate.now(),"ground","active",49.49,LocalDateTime.now(),"bad");
		assertNotNull(purchaseRequestRepo.save(v));
		assertEquals("justimafication", v.getJustification());
// Test Update
		LocalDateTime rightNow = LocalDateTime.now();
		v.setSubmittedDate(rightNow);
		v.setJustification("justfmsu");
		assertNotNull(purchaseRequestRepo.save(v));
		assertEquals("justfmsu", v.getJustification());
		assertEquals(rightNow,v.getSubmittedDate());
// Test Delete
		purchaseRequestRepo.delete(v);
		assertFalse(purchaseRequestRepo.findById(v.getId()).isPresent());
	}

	


}
