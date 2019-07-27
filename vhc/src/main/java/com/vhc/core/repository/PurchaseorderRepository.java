package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vhc.core.model.Purchaseorder;

public interface PurchaseorderRepository extends CrudRepository<Purchaseorder, Long> {

	public Purchaseorder findByPoid(long poid);

	public List<Purchaseorder> findAll();

	public List<Purchaseorder> findByCodeIgnoreCaseLikeOrSupplier_nameIgnoreCaseLike(String code, String name);

    @Query("SELECT p FROM Purchaseorder p WHERE p.supplier.supplierid=:supplierid AND p.poid NOT IN (SELECT s.purchaseorder.poid FROM Item s WHERE s.purchaseorder.poid IS NOT NULL)")
    public List<Purchaseorder> findUnusedOrders(@Param("supplierid") long supplierid);
}
