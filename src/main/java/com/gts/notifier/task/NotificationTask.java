package com.gts.notifier.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.gts.notifier.model.Event;
import com.gts.notifier.model.User;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gorbachevov
 */

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
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		log.info( String.format("%s Пользователю %s отправлено сообщение с текстом: %s", 
				LocalDateTime.now().format(format), 
				notifiedUser.getName(), 
				notifiedEvent.getMessage()) );
	}

}
