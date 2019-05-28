package com.prs.db;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

	Optional<PurchaseRequest> findByUser(User user);

//	Iterable<PurchaseRequest> findAllByStatus(String string);

	Optional<PurchaseRequest> findByStatus(String string);

//	boolean findByNotUser(User reviewer);

	Optional<PurchaseRequest> findByStatusAndNotUser(String string, User reviewer);


}