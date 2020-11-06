package com.vhc.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Brand;
import com.vhc.core.model.Category;
import com.vhc.core.model.Product;
import com.vhc.core.repository.ProductRepository;


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
		return productRepository.findByStorefrontAndDisplay(storefront, "1", pageable);
		//return productRepository.findByStorefront(storefront, pageable);
	}

	public List<Product> getByStroefrontAndName(String storefront, String name) {
		return productRepository.findByStorefrontAndNameIgnoreCaseLikeAndDisplay(storefront, "1", name);
		//return productRepository.findByStorefrontAndNameIgnoreCaseLike(storefront, name);
	}

	public List<Product> getByBrands(List<Category> categories, List<Long> brandids) {
		return productRepository.findByCategoriesAndBrand_brandidIn(categories, brandids);
	}

	public List<Product> getByBrandsDisplay(List<Category> categories, List<Long> brandids) {
		return productRepository.findByCategoriesAndBrand_brandidInAndDisplay(categories, brandids, "1");
	}

	public List<Product> getByStyles(List<Category> categories, List<Long> styleids) {
		return productRepository.findByCategoriesAndStyle_styleidIn(categories, styleids);
	}

	public List<Product> getByStylesDisplay(List<Category> categories, List<Long> styleids) {
		return productRepository.findByCategoriesAndStyle_styleidInAndDisplay(categories, styleids, "1");
	}

	public List<Product> getByPrices(List<Category> categories, String[] priceids) {
		if(priceids.length == 1) {
			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			return productRepository.findByCategoriesAndDisplayAndRetailBetween(categories, "1", from, to);
		} else if(priceids.length == 2) {

			String[] prices = priceids[0].split("<");
			BigDecimal from = new BigDecimal(prices[0]);
			BigDecimal to = new BigDecimal(prices[1]);
			String[] prices1 = priceids[1].split("<");
			BigDecimal from1 = new BigDecimal(prices1[0]);
			BigDecimal to1 = new BigDecimal(prices1[1]);
			return productRepository.findByCategoriesAndDisplayAndRetailBetweenOrRetailBetween(categories, "1", from, to, from1, to1);
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
			return productRepository.findByCategoriesAndDisplayAndRetailBetweenOrRetailBetweenOrRetailBetween(categories, "1", from, to, from1, to1, from2, to2);
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
			return productRepository.findByCategoriesAndDisplayAndRetailBetweenOrRetailBetweenOrRetailBetweenOrRetailBetween(categories, "1", from, to, from1, to1, from2, to2, from3, to3);
		}

	}

	public List<Product> getByBrand(Brand brand) {
		return productRepository.findByBrand(brand);
	}

	public List<Product> getByCategoryAndName(List<Category> categories, String name) {
		return productRepository.findByCategoriesAndNameIgnoreCaseLike(categories, name);
	}

	public List<Product> getByCategoryAndNameAndDisplay(List<Category> categories, String name) {
		return productRepository.findByCategoriesAndNameIgnoreCaseLikeAndDisplay(categories, name, "1");
	}

	public List<Product> getByCategoryAndPaging(List<Category> categories, Pageable pageable) {
		return productRepository.findByCategoriesAndDisplay(categories, "1", pageable);
	}


	@Transactional(rollbackFor=Exception.class)
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long productid) {
		productRepository.deleteById(productid);
	}
}
