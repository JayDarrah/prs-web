package com.prs.db;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

	Optional<PurchaseRequest> findByUser(User user);

	Optional<PurchaseRequest> findByStatus(String string);

	Optional<PurchaseRequest> findByStatusAndUserNot(String string, User reviewer);




}