package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;

@CrossOrigin
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
			Optional<PurchaseRequestLineItem> prli = purchaseRequestLineItemRepo.findById(id);
			if (prli.isPresent())
				jr = JsonResponse.getInstance(prli);
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
			Optional<PurchaseRequestLineItem> prli = purchaseRequestLineItemRepo.findByPurchaseRequest(purchaseRequest);
			if (prli.isPresent())
				jr = JsonResponse.getInstance(prli);
			else
				jr = JsonResponse.getInstance("No purchaseRequestLineItem found for purchase request: "+purchaseRequest);
				
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			updatePurchaseRequestTotal(prli.getPurchaseRequest());

			jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(prli));
			
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}

		
		return jr;
		
	}

	@PutMapping("")
	public JsonResponse change(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(prli.getId())) {
				if (prli.getQuantity() == 0) {
					purchaseRequestLineItemRepo.delete(prli);
				} else {
					updatePurchaseRequestTotal(prli.getPurchaseRequest());
					jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(prli));
				}
			} else {
				jr = JsonResponse.getInstance("PurchaseRequestLineItem id: "+prli.getId()+" does not exist and you are attempting to save it.");
			}
		} catch (Exception e) {
			
			jr = JsonResponse.getInstance(e);
		}
		
		return jr;
		
	}

	@DeleteMapping("")
	public JsonResponse remove(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jr = null;
		// NOTE: May need to enchance exception handling if more than one exception type needs to be caught
		try {
			if (purchaseRequestLineItemRepo.existsById(prli.getId())) {
				purchaseRequestLineItemRepo.delete(prli);
				updatePurchaseRequestTotal(prli.getPurchaseRequest());
//				jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(prli));


				jr = JsonResponse.getInstance("PurchaseRequestLineItem deleted.");
			} else {
				jr = JsonResponse.getInstance("PurchaseRequestLineItem id: "+prli.getId()+" does not exist and you are attempting to delete it.");
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
			// add to pr total
			System.out.println(x.getPurchaseRequest().getTotal()+" b4");
			x.getPurchaseRequest().setTotal(tempTotal);
			System.out.println(x.getPurchaseRequest().getTotal());
		}

	}
    
}
