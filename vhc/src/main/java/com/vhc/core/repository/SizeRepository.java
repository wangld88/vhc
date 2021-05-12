package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Size;

public interface SizeRepository extends CrudRepository<Size, Long> {

	List<Size> findAll();

	List<Size> findAllByOrderByRegionAscTypeAscSeqnumAsc();

	List<Size> findByType_typeidAndRegion_regionid(long typeid, long regionid);

	List<Size> findByType_typeidAndSizenumIgnoreCaseLike(long typeid, String sizenum);

	List<Size> findBySizenumIgnoreCaseLike(String sizenum);

	Size findBySizeid(long sizeid);

}
