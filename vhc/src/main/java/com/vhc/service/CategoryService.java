package com.vhc.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Category;
import com.vhc.repository.CategoryRepository;
import com.vhc.service.util.HibernateUtil;

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


	public Category getById(long categoryid) {
		return categoryRepository.findByCategoryid(categoryid);
	}

	public Category getByName(String name) {
		return categoryRepository.findByName(name);
	}

	public Category getByTitle(String title) {
		return categoryRepository.findByTitle(title);
	}

	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	public void delete(Category category) {
		categoryRepository.delete(category);
	}
}
