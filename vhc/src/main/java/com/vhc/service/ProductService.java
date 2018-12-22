package com.vhc.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.model.Brand;
import com.vhc.model.Category;
import com.vhc.model.Product;
import com.vhc.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;


	public Product getById(long productid) {
		return productRepository.findByProductid(productid);
	}

	public List<Product> getAll() {
		return productRepository.findAllByOrderByProductidDesc();
		//return productRepository.findAll();
	}

	public Product getByFullname(String name) {
		return productRepository.findByName(name);
	}

	public List<Product> getByName(String name) {
		return productRepository.findByNameIgnoreCaseLike(name);
	}

	public List<Product> getByStroefront(String storefront) {
		return productRepository.findByStorefront(storefront);
	}

	public List<Product> getByStroefrontAndPaging(String storefront, Pageable pageable) {
		return productRepository.findByStorefront(storefront, pageable);
	}

	public List<Product> getByStroefrontAndName(String storefront, String name) {
		return productRepository.findByStorefrontAndNameIgnoreCaseLike(storefront, name);
	}

	public List<Product> getByBrands(List<Category> categories, List<Long> brandids) {
		return productRepository.findByCategoriesAndBrand_brandidIn(categories, brandids);
	}

	public List<Product> getByStyles(List<Category> categories, List<Long> styleids) {
		return productRepository.findByCategoriesAndStyle_styleidIn(categories, styleids);
	}

	public List<Product> getByPrices(List<Category> categories, String[] priceids) {
		if(priceids.length == 1) {
			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			return productRepository.findByCategoriesAndRetailBetween(categories, from, to);
		} else if(priceids.length == 2) {

			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			String[] prices1 = priceids[1].split("<");
			BigDecimal from1 = new BigDecimal(prices1[0]);
			BigDecimal to1 = new BigDecimal(prices1[1]);
			return productRepository.findByCategoriesAndRetailBetweenOrRetailBetween(categories, from, to, from1, to1);
		} else if(priceids.length == 3) {

			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			String[] prices1 = priceids[1].split("<");
			BigDecimal from1 = new BigDecimal(prices1[0]);
			BigDecimal to1 = new BigDecimal(prices1[1]);
			String[] prices2 = priceids[2].split("<");
			BigDecimal from2 = new BigDecimal(prices2[0]);
			BigDecimal to2 = new BigDecimal(prices2[1]);
			return productRepository.findByCategoriesAndRetailBetweenOrRetailBetweenOrRetailBetween(categories, from, to, from1, to1, from2, to2);
		} else {

			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			String[] prices1 = priceids[1].split("<");
			BigDecimal from1 = new BigDecimal(prices1[0]);
			BigDecimal to1 = new BigDecimal(prices1[1]);
			String[] prices2 = priceids[2].split("<");
			BigDecimal from2 = new BigDecimal(prices2[0]);
			BigDecimal to2 = new BigDecimal(prices2[1]);
			String[] prices3 = priceids[3].split("<");
			BigDecimal from3 = new BigDecimal(prices3[0]);
			BigDecimal to3 = new BigDecimal(prices3[1]);
			return productRepository.findByCategoriesAndRetailBetweenOrRetailBetweenOrRetailBetweenOrRetailBetween(categories, from, to, from1, to1, from2, to2, from3, to3);
		}

	}

	public List<Product> getByBrand(Brand brand) {
		return productRepository.findByBrand(brand);
	}

	public List<Product> getByCategoryAndName(List<Category> categories, String name) {
		return productRepository.findByCategoriesAndNameIgnoreCaseLike(categories, name);
	}

	public List<Product> getByCategoryAndPaging(List<Category> categories, Pageable pageable) {
		return productRepository.findByCategories(categories, pageable);
	}


	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Transactional
	public void delete(long productid) {
		productRepository.delete(productid);
	}
}
