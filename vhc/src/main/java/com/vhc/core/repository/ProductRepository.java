package com.vhc.core.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.vhc.core.model.Brand;
import com.vhc.core.model.Category;
import com.vhc.core.model.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	List<Product> findAllByOrderByProductidDesc();

	List<Product> findAll();

	List<Product> findByBrand_brandidAndType_typeidAndStyle_styleid(long brandid, long typeid, long styleid);

	Product findByProductid(long productid);

	Product findByName(String name);

	List<Product> findByNameIgnoreCaseLike(String name);

	List<Product> findByStorefront(String storefront);

	List<Product> findByStorefront(String storefront, Pageable pageable);

	List<Product> findByStorefrontAndDisplay(String storefront, String display, Pageable pageable);

	List<Product> findByBrand(Brand brand);

	List<Product> findByCategoriesIn(List<Category> categories, Pageable pageable);

	List<Product> findByCategoriesInAndDisplay(List<Category> categories, String display, Pageable pageable);

	List<Product> findByCategoriesInAndNameIgnoreCaseLike(List<Category> categories, String name);

	List<Product> findByCategoriesInAndNameIgnoreCaseLikeAndDisplay(List<Category> categories, String name, String display);

	List<Product> findByStorefrontAndNameIgnoreCaseLike(String storefront, String name);

	List<Product> findByStorefrontAndNameIgnoreCaseLikeAndDisplay(String storefront, String name, String display);

	List<Product> findByCategoriesInAndBrand_brandidIn(List<Category> categories, List<Long> brandids);

	List<Product> findByCategoriesInAndBrand_brandidInAndDisplay(List<Category> categories, List<Long> brandids, String display);

	List<Product> findByCategoriesInAndStyle_styleidIn(List<Category> categories, List<Long> styleids);

	List<Product> findByCategoriesInAndRetailBetween(List<Category> categories, BigDecimal from, BigDecimal to);

	List<Product> findByCategoriesInAndStyle_styleidInAndDisplay(List<Category> categories, List<Long> styleids, String display);

	List<Product> findByCategoriesInAndDisplayAndRetailBetween(List<Category> categories, String display, BigDecimal from, BigDecimal to);

	List<Product> findByCategoriesInAndDisplayAndRetailBetweenOrRetailBetween(List<Category> categories, String display, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1);

	List<Product> findByCategoriesInAndDisplayAndRetailBetweenOrRetailBetweenOrRetailBetween(List<Category> categories, String display, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1, BigDecimal from2, BigDecimal to2);

	List<Product> findByCategoriesInAndDisplayAndRetailBetweenOrRetailBetweenOrRetailBetweenOrRetailBetween(List<Category> categories, String display, BigDecimal from, BigDecimal to, BigDecimal from1, BigDecimal to1, BigDecimal from2, BigDecimal to2, BigDecimal from3, BigDecimal to3);

	/*@Query("SELECT p FROM Product p WHERE EXISTS ( SELECT o FROM Categoryproduct o WHERE o.productid = p.productid AND categoryid = ?1)")
	List<Product> findByCategory(long categoryid);*/

	/*@Query("SELECT p FROM Product p WHERE NOT EXISTS ( SELECT o FROM Categoryproduct o WHERE o.productid = p.productid AND categoryid = ?1)")
	List<Product> findNotInCategory(long categoryid);*/

	//List<Product> findBySupplier(Supplier supplier);

}
