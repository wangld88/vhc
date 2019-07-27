package com.vhc.core.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Category;
import com.vhc.core.repository.CategoryRepository;
import com.vhc.core.service.util.HibernateUtil;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private HibernateUtil hibernateUtil;


	public LobCreator getLobCreator() {
		Session session = hibernateUtil.openSession();

		return Hibernate.getLobCreator(session);
	}


	@Transactional(readOnly=true)
	public Category getById(long categoryid) {
		return categoryRepository.findByCategoryid(categoryid);
	}

	@Transactional(readOnly=true)
	public Category getByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Transactional(readOnly=true)
	public Category getByTitle(String title) {
		return categoryRepository.findByTitle(title);
	}

	@Transactional(readOnly=true)
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(Category category) {
		categoryRepository.delete(category);
	}
}
