package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vhc.core.model.Countlog;
import com.vhc.core.model.Countupload;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.Inventorycount;


@Repository
public interface CountlogRepository extends CrudRepository<Countlog, Long> {

	Countlog findByCountlogid(long id);

	List<Countlog> findByUploadOrderByCountlogid(Countupload upload);

	List<Countlog> findBySku(String sku);

	List<Countlog> findByInventory(Inventory inventory);

	List<Countlog> findByUpload_countAndInventoryIsNullOrderByCountlogid(Inventorycount count);

	List<Countlog> findAll();

}
