package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Promocode;
import com.vhc.core.repository.PromocodeRepository;

@Service
public class PromocodeService {

	@Autowired
	private PromocodeRepository promocodeRepository;

	public Promocode getById(long promocodeid) {
		return promocodeRepository.findByPromocodeid(promocodeid);
	}

	public Promocode getByCode(String promocode) {
		return promocodeRepository.findByCode(promocode);
	}

	public List<Promocode> getAll() {
		return promocodeRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public void save(Promocode promocode) {
		this.promocodeRepository.save(promocode);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(long promocodeid) {
		this.promocodeRepository.delete(promocodeid);
	}

}
