package com.vhc.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Promocode;
import com.vhc.repository.PromocodeRepository;

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

	public void save(Promocode promocode) {
		this.promocodeRepository.save(promocode);
	}

}
