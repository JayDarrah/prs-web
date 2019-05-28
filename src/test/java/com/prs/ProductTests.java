package com.prs;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.Vendor;
import com.prs.business.Product;
import com.prs.db.ProductRepository;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductTests {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private VendorRepository vendorRepo;
	@Test
	public void testProductGetAll() {
		// Get All Products
		Iterable<Product> products = productRepo.findAll();
		assertNotNull(products);
	}
	// Test add update and delete
	@Before
	public void testProductAdd() {
		// Add a product
		Iterable<Vendor> vendors = vendorRepo.findAll();
		assertNotNull(vendors);
		Vendor v = vendors.iterator().next();
		Product p= new Product(v, "123456","PName",69.69,"dkdk","photopath");
		assertNotNull(productRepo.save(p));
		assertEquals("PName", p.getName());
		p.setName("newnameyeah");
		assertNotNull(productRepo.save(p));
		assertEquals("newnameyeah", p.getName());
		productRepo.delete(p);
		assertFalse(productRepo.findById(p.getId()).isPresent());
	}

	


}
