package com.vhc.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.vhc.dto.ItemForm;

public class ItemFormValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ItemFormValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(ItemForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("ItemForm Validate");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product", "product.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "size", "size.required");

	}

}
