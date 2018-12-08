package com.vhc.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;


@Component
public class MessageHandler {

	@Autowired
	private MessageSource messageSource;
	
	private MessageSourceAccessor accessor;
	
	
	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
	}
	
	public String get(String key) {
		return accessor.getMessage(key);
	}
	
	public String get(String key, Object[] args) {
		return accessor.getMessage(key, args);
	}

}
