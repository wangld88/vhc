package com.vhc.core.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Orderitem;


public interface OrderitemRepository extends CrudRepository<Orderitem, Long> {

	public Orderitem findByOrderitemid(long orderitemid);

	public List<Orderitem> findAll();

	public List<Orderitem> findAllByOrderByOrder_creationdateDesc();

	public List<Orderitem> findByOrder_creationdateGreaterThanEqualAndOrder_creationdateLessThanEqual(Calendar from, Calendar to);

	public List<Orderitem> findByOrder_store_storeidAndOrder_creationdateGreaterThanEqualAndOrder_creationdateLessThanEqual(long storeid, Calendar from, Calendar to);

	public List<Orderitem> findByItem_product_nameLikeOrItem_product_modelnumLikeOrItem_skuLikeOrderByOrder_creationdateDesc(String name, String model, String sku);

}
