package com.gts.notifier.service;

import com.gts.notifier.model.Event;

public interface NotificationService {

	void sendNotify(Event event);
	
}
