package com.prs.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;

@RestController
@RequestMapping("/purchase-requests")
public class PurchaseRequestController {
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;

    @GetMapping("/")
    public JsonResponse getAll() {
            JsonResponse jr = null;
            try {
                    jr = JsonResponse.getInstance(purchaseRequestRepo.findAll());

            } catch (Exception e) {
                    jr = JsonResponse.getInstance(e);
            }
            return jr;
    }

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> u = purchaseRequestRepo.findById(id);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No purchaseRequest found for id: "+id);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/get-by-user")
	public JsonResponse getByUser(@RequestBody User usr) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> u = purchaseRequestRepo.findByUser(usr);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No purchaseRequest found for User: "+usr);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/list-review")
	public JsonResponse requestReview(@RequestBody User reviewer) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> u = purchaseRequestRepo.findByStatusAndNotUser("Review",reviewer);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No purchaseRequest found with status: Review");
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/submit-new")
	public JsonResponse add(@RequestBody User u) {
		JsonResponse jr = null;
		PurchaseRequest pr = new PurchaseRequest();
		pr.setStatus("New");
		pr.setSubmittedDate(LocalDateTime.now());
		pr.setUser(u);
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@PostMapping("/submit-review")
	public JsonResponse submitForReview(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		if (u.getTotal() <= 50.0) {
			u.setStatus("Approved");
		} else {
			u.setStatus("Review");
		}
		u.setSubmittedDate(LocalDateTime.now());
		
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.save(u));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@PutMapping("")
	public JsonResponse update(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(u));
			} else {
				jr = JsonResponse.getInstance("PurchaseRequest id: "+u.getId()+" does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}

	@PutMapping("/approve")
	public JsonResponse approve(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				u.setStatus("Approved");
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(u));
			} else {
				jr = JsonResponse.getInstance("PurchaseRequest id: "+u.getId()+" does not exist and you are attempting to approve it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/reject")
	public JsonResponse reject(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				u.setStatus("Rejected");
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(u));
			} else {
				jr = JsonResponse.getInstance("PurchaseRequest id: "+u.getId()+" does not exist and you are attempting to reject it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	
	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequest u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestRepo.existsById(u.getId())) {
				purchaseRequestRepo.delete(u);
				jr = JsonResponse.getInstance("PurchaseRequest deleted.");
			} else {
				jr = JsonResponse.getInstance("PurchaseRequest id: "+u.getId()+" does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}
	

    
}
