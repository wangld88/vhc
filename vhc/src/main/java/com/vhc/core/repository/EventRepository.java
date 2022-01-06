package com.vhc.core.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vhc.core.model.Event;
import com.vhc.core.model.Status;


public interface EventRepository extends CrudRepository<Event, Long> {

	Event findByEventid(long id);

	Event findByStartdateBeforeAndEnddateAfterAndStatus(Calendar sd, Calendar ed, Status status);

	Event findByEnddateAndStatus(Calendar ed, Status status);

	List<Event> findByEnddateBeforeAndStatus(Calendar ed, Status status);

	List<Event> findAll();

}
