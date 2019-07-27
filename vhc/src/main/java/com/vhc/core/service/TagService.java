package com.vhc.core.service;

import com.vhc.core.model.Tag;
import com.vhc.core.repository.TagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;


	public Tag getById(long tagid) {
		return tagRepository.findByTagid(tagid);
	}

	public List<Tag> getAll() {
		return tagRepository.findAll();
	}

	@Transactional(rollbackFor=Exception.class)
	public Tag save(Tag tag) {
		return (Tag)this.tagRepository.save(tag);
	}
}
