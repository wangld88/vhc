package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.core.model.Page;
import com.vhc.core.model.Pageimage;
import com.vhc.core.repository.PageimageRepository;


@Service
public class PageimageService {

	@Autowired
	private PageimageRepository pageimageRepository;

	public Pageimage getById(long pageimageid) {

		return pageimageRepository.findByPageimageid(pageimageid);

	}

	public List<Pageimage> getByPage(Page page) {

		return pageimageRepository.findByPageOrderBySeqnum(page);

	}

	public List<Pageimage> getAll() {

		return pageimageRepository.findAll();

	}

	public Pageimage save(Pageimage pageimage) {

		return pageimageRepository.save(pageimage);

	}

	public void delete(Pageimage pageimage) {

		pageimageRepository.delete(pageimage);

	}
}
