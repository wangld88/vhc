package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vhc.core.model.Countinventory;
import com.vhc.core.model.Inventorycount;


@Repository
public interface CountinventoryRepository extends CrudRepository<Countinventory, Long> {

	Countinventory findByCountinventoryid(long id);

	List<Countinventory> findBySku(String sku);

	List<Countinventory> findBySkuAndCountlogIsNull(String sku);

	List<Countinventory> findByCount(Inventorycount count);

	List<Countinventory> findByCountAndCountlogIsNullOrderByCountinventoryid(Inventorycount count);

	List<Countinventory> findByCountAndCountlogIsNotNull(Inventorycount count);

}
