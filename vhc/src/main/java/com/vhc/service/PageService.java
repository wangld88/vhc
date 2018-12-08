package com.vhc.service;

import com.vhc.model.Page;
import com.vhc.repository.PageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService {

	@Autowired
	private PageRepository pageRepository;


	public Page getById(long pageid) {
		return pageRepository.findByPageid(pageid);
	}

	public List<Page> getAll() {
		return pageRepository.findAll();
	}

	public Page save(Page page) {
		return (Page)this.pageRepository.save(page);
	}
}
