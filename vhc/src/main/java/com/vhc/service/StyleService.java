package com.vhc.service;

import com.vhc.model.Style;
import com.vhc.repository.StyleRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StyleService {

	@Autowired
	private StyleRepository styleRepository;


	public Style getById(long styleid) {
		return styleRepository.findByStyleid(styleid);
	}

	public List<Style> getAll() {
		return styleRepository.findAll();
	}

	public List<Style> getByName(String name) {
		return styleRepository.findByNameIgnoreCaseLike(name);
	}

	public Style save(Style style) {
		return (Style)this.styleRepository.save(style);
	}
}
