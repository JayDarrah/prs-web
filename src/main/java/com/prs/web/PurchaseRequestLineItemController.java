package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;

@RestController
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {
	
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepo;

    @GetMapping("/")
    public JsonResponse getAll() {
            JsonResponse jr = null;
            try {
                    jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.findAll());

            } catch (Exception e) {
                    jr = JsonResponse.getInstance(e);
            }
            return jr;
    }

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> u = purchaseRequestLineItemRepo.findById(id);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No purchaseRequestLineItem found for id: "+id);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("")
	public JsonResponse getByPurchaseRequest(@RequestParam int purchaseRequest) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> u = purchaseRequestLineItemRepo.findByPurchaseRequest(purchaseRequest);
			if (u.isPresent())
				jr = JsonResponse.getInstance(u);
			else
				jr = JsonResponse.getInstance("No purchaseRequestLineItem found for purchase request: "+purchaseRequest);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(u));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}

		updatePurchaseRequestTotal(u.getPurchaseRequest());
		
		return jr;
		
	}

	@PutMapping("")
	public JsonResponse change(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(u.getId())) {
				if (u.getQuantity() == 0) {
					purchaseRequestLineItemRepo.delete(u);
				} else {
					jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(u));
					updatePurchaseRequestTotal(u.getPurchaseRequest());
				}
			} else {
				jr = JsonResponse.getInstance("PurchaseRequestLineItem id: "+u.getId()+" does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
		
	}

	@DeleteMapping("")
	public JsonResponse remove(@RequestBody PurchaseRequestLineItem u) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(u.getId())) {
				purchaseRequestLineItemRepo.delete(u);
				updatePurchaseRequestTotal(u.getPurchaseRequest());

				jr = JsonResponse.getInstance("PurchaseRequestLineItem deleted.");
			} else {
				jr = JsonResponse.getInstance("PurchaseRequestLineItem id: "+u.getId()+" does not exist and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
		
	}
	
	public void updatePurchaseRequestTotal(PurchaseRequest pr) {
		Iterable<PurchaseRequestLineItem> prli = purchaseRequestLineItemRepo.findByPurchaseRequest(pr);
		double tempTotal = 0.0;
		for (PurchaseRequestLineItem x: prli) {
			System.out.println(x);
			tempTotal+=(x.getProduct().getPrice()*x.getQuantity());
		}
		// add to pr total
		pr.setTotal(tempTotal);
		System.out.println(pr.getTotal());
		
	}
    
}
