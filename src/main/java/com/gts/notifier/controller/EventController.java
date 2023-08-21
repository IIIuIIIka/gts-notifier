package com.gts.notifier.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gts.notifier.dto.EventDTO;
import com.gts.notifier.model.Event;
import com.gts.notifier.service.EventService;

import lombok.AllArgsConstructor;

/**
 * REST API controller for {@link Event} entities
 * @author gorbachevov
 */

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

	private EventService eventService;
	
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody EventDTO event) {
		Event created = convertFromDTO(event);
		created = eventService.create(created);
		return ResponseEntity.ok( convertFromEntity(created) );
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(eventService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Long id) {
		Optional<Event> exist = eventService.findById(id);
		if( exist.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( convertFromEntity(exist.get()) );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventById(@PathVariable Long id, @RequestBody EventDTO event) {
		Optional<Event> exist = eventService.findById(id);
		if( exist.isEmpty() )
			return ResponseEntity.notFound().build();
		Event updated = convertFromDTO(event);
		updated.setId( exist.get().getId() );
		eventService.update(updated);
		return ResponseEntity.ok( convertFromEntity(updated) );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		 Optional<Event> exist = eventService.findById(id);
		 if( exist.isEmpty() ) {
			 return ResponseEntity.notFound().build();
		 }
		 eventService.delete( exist.get() );
		 return ResponseEntity.ok().build();
	}
	
	private Event convertFromDTO(EventDTO event) {
		return modelMapper.map(event, Event.class);
	}
	
	private EventDTO convertFromEntity(Event event) {
		return modelMapper.map(event, EventDTO.class);
	}
}
