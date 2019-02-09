package com.vhc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.model.Template;
import com.vhc.repository.TemplateRepository;


@Service
public class TemplateService {

	@Autowired
	private TemplateRepository templateRepository;


	public Template getById(int templateid) {
		return templateRepository.findByTemplateid(templateid);
	}

	public List<Template> getAll() {
		return templateRepository.findAll();
	}

	public Template save(Template template) {
		return templateRepository.save(template);
	}
}
