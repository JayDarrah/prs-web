package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Product;
import com.prs.db.ProductRepository;

@RestController
@RequestMapping("/Products")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;

    @GetMapping("/")
    public JsonResponse getAll() {
            JsonResponse jr = null;
            try {
                    jr = JsonResponse.getInstance(productRepo.findAll());

            } catch (Exception e) {
                    jr = JsonResponse.getInstance(e);
            }
            return jr;
    }

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Product> u = productRepo.findById(id);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No product found for id: "+id);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("")
	public JsonResponse getByPartNumber(@RequestParam String partNumber) {
		JsonResponse jr = null;
		try {
			Optional<Product> u = productRepo.findByPartNumber(partNumber);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No product found for part number: "+partNumber);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody Product u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(productRepo.save(u));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody Product u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (productRepo.existsById(u.getId())) {
				jr = JsonResponse.getInstance(productRepo.save(u));
			} else {
				jr = JsonResponse.getInstance("Product id: "+u.getId()+" does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody Product u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (productRepo.existsById(u.getId())) {
				productRepo.delete(u);
				jr = JsonResponse.getInstance("Product deleted.");
			} else {
				jr = JsonResponse.getInstance("Product id: "+u.getId()+" does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}
	

    
}
