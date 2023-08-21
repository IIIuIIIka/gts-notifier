package com.gts.notifier.listener;

import java.time.LocalDateTime;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.gts.notifier.model.Event;
import com.gts.notifier.service.NotificationService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;

/**
 * Custom JPA listener for {@link Event}
 * @author gorbachevov
 */

@Configurable
public class EventListener {
	
	@Autowired
	private ObjectFactory<NotificationService> serviceProvider; 

	@PrePersist
	void onPrePersist(Object event) {
		if( event instanceof Event e ) {
			e.setEventDateTime( LocalDateTime.now() );
		}
	}
	
	@PostPersist
	void onPostPersist(Object event) {
		if( event instanceof Event e ) {
			serviceProvider.getObject().sendNotify( e );
		}
	}
}
