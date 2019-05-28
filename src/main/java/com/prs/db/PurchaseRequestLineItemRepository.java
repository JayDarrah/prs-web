package com.prs.db;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer> {


	Optional<PurchaseRequestLineItem> findByPurchaseRequest(int purchaseRequest);
	Iterable<PurchaseRequestLineItem> findByPurchaseRequest(PurchaseRequest purchaseRequest);

}