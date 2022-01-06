package com.vhc.core.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vhc.core.model.Event;
import com.vhc.core.model.Status;
import com.vhc.core.repository.EventRepository;


@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Transactional(readOnly=true)
	public Event getById(long id) {

		return eventRepository.findByEventid(id);

	}

	@Transactional(readOnly=true)
	public List<Event> getPastEvent(Status status) {
		Calendar today = Calendar.getInstance();
		return eventRepository.findByEnddateBeforeAndStatus(today, status);
	}
	
	@Transactional(readOnly=true)
	public Event getCurrentEvent(Status status) {

		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);

		return eventRepository.findByStartdateBeforeAndEnddateAfterAndStatus(tomorrow, yesterday, status);

	}

	@Transactional(readOnly=true)
	public Event getEndedEvent(Status status) {

		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, 1);

		return eventRepository.findByEnddateAndStatus(yesterday, status);

	}

	@Transactional(readOnly=true)
	public List<Event> getAll() {

		return eventRepository.findAll();

	}

	@Transactional(rollbackFor=Exception.class)
	public Event save(Event event) {

		return eventRepository.save(event);

	}
}
