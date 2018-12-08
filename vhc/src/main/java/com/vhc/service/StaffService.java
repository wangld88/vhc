package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Staff;
import com.vhc.model.User;
import com.vhc.repository.StaffRepository;

@Service
public class StaffService {

	@Autowired StaffRepository staffRespository;


	public Staff getById(long staffid) {
		return staffRespository.findByStaffid(staffid);
	}

	public Staff getByUser(User user) {
		return staffRespository.findByUser(user);
	}

	public List<Staff> getAll() {
		return staffRespository.findAll();
	}

	public Staff save(Staff staff) {
		return staffRespository.save(staff);
	}
}
