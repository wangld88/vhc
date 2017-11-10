package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Color;
import com.vhc.repository.ColorRepository;

@Service
public class ColorService {

	@Autowired
	ColorRepository colorRepository;
	
	
	public List<Color> getAll() {
		return colorRepository.findAll();
	}
	
	public Color getById(long colorid) {
		return colorRepository.findByColorid(colorid);
	}
	
	public Color save(Color color) {
		return this.colorRepository.save(color);
	}
	
}
