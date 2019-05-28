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
import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VendorTests {

	@Autowired
	private VendorRepository vendorRepo;

	@Test
	public void testVendorGetAll() {
		// Get All Vendors
		Iterable<Vendor> vendors = vendorRepo.findAll();
		assertNotNull(vendors);
	}
	// Test add update and delete
	@Before
	public void testVendorAdd() {
		// Add a vendor
		Vendor v= new Vendor("code","Cody's Coding","3838383838","Cincy","OH","45454","4128384938","emailyeah",true);
		assertNotNull(vendorRepo.save(v));
		assertEquals("Cody's Coding", v.getName());
		v.setEmail("emailemailemail");
		assertNotNull(vendorRepo.save(v));
		assertEquals("emailemailemail", v.getEmail());
		vendorRepo.delete(v);
		assertFalse(vendorRepo.findById(v.getId()).isPresent());
	}

	


}
