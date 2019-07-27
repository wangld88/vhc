package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Recipient;
import com.vhc.core.repository.RecipientRepository;

@Service
public class RecipientService {

	@Autowired
	private RecipientRepository recipientRepository;

	public Recipient getById(long recipientid) {

		return recipientRepository.findByRecipientid(recipientid);

	}


	public List<Recipient> getById(Customer customer) {

		return recipientRepository.findByCreatedby(customer);

	}

	public List<Recipient> getAll() {

		return recipientRepository.findAll();

	}

}
