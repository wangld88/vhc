package com.vhc.service;

import com.vhc.model.Categoryproduct;
import com.vhc.repository.CategoryproductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryproductService {

	@Autowired
	private CategoryproductRepository categoryproductRepository;


	public Categoryproduct getById(long cateprodid) {
		return categoryproductRepository.findByCateprodid(cateprodid);
	}

	public List<Categoryproduct> getByCategoryid(long categoryid) {
		return categoryproductRepository.findByCategory_categoryid(categoryid);
	}

	public Categoryproduct getByCategoryidProductid(long categoryid, long productid) {
		return categoryproductRepository.findByCategory_categoryidAndProduct_productid(categoryid, productid);
	}

	public List<Categoryproduct> getAll() {
		return categoryproductRepository.findAll();
	}

	public Categoryproduct save(Categoryproduct categoryproduct) {
		return (Categoryproduct)this.categoryproductRepository.save(categoryproduct);
	}

	public void delete(Categoryproduct cp) {
		this.categoryproductRepository.delete(cp);
	}
}
