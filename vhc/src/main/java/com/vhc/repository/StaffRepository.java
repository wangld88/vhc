package com.vhc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.model.Staff;
import com.vhc.model.User;

public interface StaffRepository extends CrudRepository<Staff, Long> {

	public Staff findByStaffid(long staffid);
	public Staff findByUser(User user);
	public List<Staff> findAll();

}
