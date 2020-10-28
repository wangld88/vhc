package com.vhc.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;

public interface StaffRepository extends CrudRepository<Staff, Long> {

	public Staff findByStaffid(long staffid);
	public Staff findByUser(User user);
	public List<Staff> findAll();
	public List<Staff> findByStore(Store store);

}
