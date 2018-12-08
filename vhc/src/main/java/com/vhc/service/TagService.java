package com.vhc.service;

import com.vhc.model.Tag;
import com.vhc.repository.TagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Tag save(Tag tag) {
		return (Tag)this.tagRepository.save(tag);
	}
}
