package com.gts.notifier.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gts.notifier.model.Event;
import com.gts.notifier.repository.EventRepository;
import com.gts.notifier.service.EventService;

import lombok.AllArgsConstructor;

/**
 * Implementation of {@link EventService}
 * @author gorbachevov
 */

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;
	
	@Override
	public Optional<Event> findById(Long id) {
		return eventRepository.findById(id);
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public Event create(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public Event update(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public void delete(Long id) {
		eventRepository.deleteById(id);
	}

}
