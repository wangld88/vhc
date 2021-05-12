package com.vhc.core.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Image;
import com.vhc.core.repository.ImageRepository;
import com.vhc.core.service.util.HibernateUtil;

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


	@Transactional(readOnly=true)
	public Image getById(long imageid) {
		return imageRepository.findByImageid(imageid);
	}

	@Transactional(readOnly=true)
	public List<Image> getByProduct(long productid) {
		return imageRepository.findByProduct_productid(productid);
	}

	@Transactional(readOnly=true)
	public List<Image> getAll() {
		return imageRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Image save(Image image) {
		return imageRepository.save(image);
	}

	@Transactional(rollbackFor=Exception.class)
	public void delete(Image image) {
		imageRepository.delete(image);
	}
}
