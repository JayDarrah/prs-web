package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RestController
@RequestMapping("/Vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository vendorRepo;

    @GetMapping("/")
    public JsonResponse getAll() {
            JsonResponse jr = null;
            try {
                    jr = JsonResponse.getInstance(vendorRepo.findAll());

            } catch (Exception e) {
                    jr = JsonResponse.getInstance(e);
            }
            return jr;
    }

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> u = vendorRepo.findById(id);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No vendor found for id: "+id);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("")
	public JsonResponse getByCode(@RequestParam String code) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> u = vendorRepo.findByCode(code);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No product found for code: "+code);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody Vendor u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(vendorRepo.save(u));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody Vendor u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (vendorRepo.existsById(u.getId())) {
				jr = JsonResponse.getInstance(vendorRepo.save(u));
			} else {
				jr = JsonResponse.getInstance("Vendor id: "+u.getId()+" does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody Vendor u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (vendorRepo.existsById(u.getId())) {
				vendorRepo.delete(u);
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
