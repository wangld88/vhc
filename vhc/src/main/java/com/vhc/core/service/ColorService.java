package com.vhc.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Color;
import com.vhc.core.repository.ColorRepository;

@Service
public class ColorService {

	@Autowired
	ColorRepository colorRepository;


	@Transactional(readOnly=true)
	public List<Color> getAll() {
		return colorRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Color> getByName(String name) {
		return colorRepository.findByNameIgnoreCaseLike(name);
	}

	@Transactional(readOnly=true)
	public Color getById(long colorid) {
		return colorRepository.findByColorid(colorid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Color save(Color color) {
		return this.colorRepository.save(color);
	}

}
