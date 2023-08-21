package com.gts.notifier.service;

import java.util.List;
import java.util.Optional;

import com.gts.notifier.model.Event;

/**
 * @author gorbachevov
 */
public interface EventService {

	Optional<Event> findById(Long id);
	List<Event> findAll();
	
	Event create(Event event);
	Event update(Event event);
	void delete(Long id);
}
