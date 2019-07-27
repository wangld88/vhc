package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Recipient;

public interface RecipientRepository extends CrudRepository<Recipient, Long> {

	Recipient findByRecipientid(long recipientid);

	List<Recipient> findAll();

	List<Recipient> findByCreatedby(Customer customer);

}
