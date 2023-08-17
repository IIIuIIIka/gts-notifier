package com.gts.notifier.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.gts.notifier.model.Event;
import com.gts.notifier.service.NotificationService;

import jakarta.persistence.PostPersist;

@Configurable
public class EventListener {
	
	@Autowired
	private NotificationService notificationService; 
	
	@PostPersist
	void onPostPersist(Object event) {
		if( event instanceof Event e) {
			notificationService.sendNotify( e );
		}
	}
}
