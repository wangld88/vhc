package com.vhc.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vhc.model.Brand;
import com.vhc.model.Category;
import com.vhc.model.Product;
//import com.vhc.model.Supplier;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	List<Product> findAllByOrderByProductidDesc();

	List<Product> findAll();

	Product findByProductid(long productid);

	Product findByName(String name);

	List<Product> findByNameIgnoreCaseLike(String name);

	List<Product> findByStorefront(String storefront);

	List<Product> findByBrand(Brand brand);

	List<Product> findByCategories(List<Category> categories, Pageable pageable);

	List<Product> findByCategoriesAndNameIgnoreCaseLike(List<Category> categories, String name);

	List<Product> findByStorefrontAndNameIgnoreCaseLike(String storefront, String name);

	List<Product> findByCategoriesAndBrand_brandidIn(List<Category> categories, List<Long> brandids);

	List<Product> findByCategoriesAndStyle_styleidIn(List<Category> categories, List<Long> styleids);

	List<Product> findByCategoriesAndRetailBetween(List<Category> categories, BigDecimal from, BigDecimal to);

	List<Product> findByCategoriesAndRetailBetweenOrRetailBetween(List<Category> categories, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1);

	List<Product> findByCategoriesAndRetailBetweenOrRetailBetweenOrRetailBetween(List<Category> categories, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1, BigDecimal from2, BigDecimal to2);

	List<Product> findByCategoriesAndRetailBetweenOrRetailBetweenOrRetailBetweenOrRetailBetween(List<Category> categories, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1, BigDecimal from2, BigDecimal to2, BigDecimal from3, BigDecimal to3);

	/*@Query("SELECT p FROM Product p WHERE EXISTS ( SELECT o FROM Categoryproduct o WHERE o.productid = p.productid AND categoryid = ?1)")
	List<Product> findByCategory(long categoryid);*/

	/*@Query("SELECT p FROM Product p WHERE NOT EXISTS ( SELECT o FROM Categoryproduct o WHERE o.productid = p.productid AND categoryid = ?1)")
	List<Product> findNotInCategory(long categoryid);*/

	//List<Product> findBySupplier(Supplier supplier);

}
