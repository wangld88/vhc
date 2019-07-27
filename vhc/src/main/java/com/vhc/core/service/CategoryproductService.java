package com.vhc.core.service;

import com.vhc.core.model.Categoryproduct;
import com.vhc.core.repository.CategoryproductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryproductService {

	@Autowired
	private CategoryproductRepository categoryproductRepository;


	@Transactional(readOnly=true)
	public Categoryproduct getById(long cateprodid) {
		return categoryproductRepository.findByCateprodid(cateprodid);
	}

	@Transactional(readOnly=true)
	public List<Categoryproduct> getByCategoryid(long categoryid) {
		return categoryproductRepository.findByCategory_categoryid(categoryid);
	}

	@Transactional(readOnly=true)
	public Categoryproduct getByCategoryidProductid(long categoryid, long productid) {
		return categoryproductRepository.findByCategory_categoryidAndProduct_productid(categoryid, productid);
	}

	@Transactional(readOnly=true)
	public List<Categoryproduct> getAll() {
		return categoryproductRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Categoryproduct save(Categoryproduct categoryproduct) {
		return (Categoryproduct)this.categoryproductRepository.save(categoryproduct);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(Categoryproduct cp) {
		this.categoryproductRepository.delete(cp);
	}
}
