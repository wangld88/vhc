package com.vhc.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Image;
import com.vhc.repository.ImageRepository;
import com.vhc.service.util.HibernateUtil;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	
	public LobCreator getLobCreator() {
		Session session = hibernateUtil.openSession();
		
		return Hibernate.getLobCreator(session);
	}

	
	public Image getById(long imageid) {
		return imageRepository.findByImageid(imageid);
	}
	
	public List<Image> getByProduct(long productid) {
		return imageRepository.findByProduct_productid(productid);
	}
	
	public List<Image> getAll() {
		return imageRepository.findAll();
	}
	
	public Image save(Image image) {
		return imageRepository.save(image);
	}
}
