package com.gts.notifier.task;

import com.gts.notifier.model.Event;
import com.gts.notifier.model.User;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NotificationTask implements Runnable {

	private User notifiedUser;
	private Event notifiedEvent;
	
	public NotificationTask(User user, Event event) {
		this.notifiedUser = user;
		this.notifiedEvent = event;
	}
	
	@Override
	public void run() {
		log.info( String.format("%s Пользователю %s отправлено сообщение с текстом: %s", 
				notifiedEvent.getEventDateTime(), 
				notifiedUser.getName(), 
				notifiedEvent.getMessage()) );
	}

}
