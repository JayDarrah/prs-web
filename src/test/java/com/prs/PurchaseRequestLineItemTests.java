package com.prs;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.User;
import com.prs.business.Product;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.ProductRepository;
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestLineItemTests {

	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepo;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	@Autowired
	private ProductRepository productRepo;
	@Test
	public void testPurchaseRequestLineItemGetAll() {
		// Get All PurchaseRequestLineItems
		Iterable<PurchaseRequestLineItem> purchaseRequestLineItems = purchaseRequestLineItemRepo.findAll();
		assertNotNull(purchaseRequestLineItems);
	}
	// Test add update and delete
	@Before
	public void testPurchaseRequestLineItemAdd() {
		Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepo.findAll();
		PurchaseRequest pr = purchaseRequests.iterator().next();
		Iterable<Product> products = productRepo.findAll();
		Product p = products.iterator().next();
		
		PurchaseRequestLineItem prli= new PurchaseRequestLineItem(pr, p,4);
		assertNotNull(purchaseRequestLineItemRepo.save(prli));
		assertEquals(4, prli.getQuantity());
// Test Update
		prli.setQuantity(5);
		assertNotNull(purchaseRequestLineItemRepo.save(prli));
		assertEquals(5, prli.getQuantity());
// Test Delete
		purchaseRequestLineItemRepo.delete(prli);
		assertFalse(purchaseRequestLineItemRepo.findById(prli.getId()).isPresent());
	}

	


}
