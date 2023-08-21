package com.gts.notifier.service;

import com.gts.notifier.model.Event;

/**
 * @author gorbachevov
 */

public interface NotificationService {

	void sendNotify(Event event);
	
}
