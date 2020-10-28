package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;
import com.vhc.core.repository.StaffRepository;

@Service
public class StaffService {

	@Autowired StaffRepository staffRespository;


	@Transactional(readOnly=true)
	public Staff getById(long staffid) {
		return staffRespository.findByStaffid(staffid);
	}

	@Transactional(readOnly=true)
	public Staff getByUser(User user) {
		return staffRespository.findByUser(user);
	}

	@Transactional(readOnly=true)
	public List<Staff> getAll() {
		return staffRespository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Staff> getByStore(Store store) {
		return staffRespository.findByStore(store);
	}

	@Transactional(rollbackFor=Exception.class)
	public Staff save(Staff staff) {
		return staffRespository.save(staff);
	}
}
