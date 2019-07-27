package com.vhc.core.service;

import com.vhc.core.model.Page;
import com.vhc.core.repository.PageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(rollbackFor=Exception.class)
	public Page save(Page page) {
		return (Page)this.pageRepository.save(page);
	}
}
