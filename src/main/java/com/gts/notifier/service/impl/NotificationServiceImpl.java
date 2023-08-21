package com.gts.notifier.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.gts.notifier.data.UserTimeSlot;
import com.gts.notifier.model.Event;
import com.gts.notifier.model.User;
import com.gts.notifier.service.NotificationService;
import com.gts.notifier.service.UserService;
import com.gts.notifier.task.NotificationTask;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationServiceImpl implements NotificationService {

	private UserService userService;
	
	private ThreadPoolTaskExecutor taskExecutor;
	
	private ThreadPoolTaskScheduler taskScheduler;
		
	@Override
	public void sendNotify(Event event) {
		log.info("Successfully injected into listener");
		List<User> users = userService.findAll();
		users.stream()
			 .map( u -> new NotificationTask(u, event) )
			 .forEach( nt -> notifyTask(nt) );
	}

	@Async
	private void notifyTask(NotificationTask task) {
		if( task.getNotifiedUser().getTimeSlots() == null ) {
			taskExecutor.execute( task );
		} else {
			LinkedList<UserTimeSlot> weekDayIntervals = new LinkedList<UserTimeSlot>( task.getNotifiedUser().getTimeSlots() );
			LocalDateTime event = task.getNotifiedEvent().getEventDateTime();
			if( weekDayIntervals
					.stream()
					.filter(ts -> 
							ts.getWeekDay().equals( event.getDayOfWeek() ) &&
							ts.getStartTime().isBefore( event.toLocalTime() ) && 
							ts.getEndTime().isAfter( event.toLocalTime() )
						)
					.toList()
					.size() > 0 ) {
				taskExecutor.execute( task );
			} else {
				taskScheduler.schedule( task, getScheduledStart( weekDayIntervals, event.plusDays(1) ) );
			}
		}
	}
	
	private Instant getScheduledStart( LinkedList<UserTimeSlot> slots, LocalDateTime event ) {
		UserTimeSlot slot = slots.stream()
			.filter(ts -> 
					ts.getWeekDay().equals( event.getDayOfWeek() ) &&
					ts.getStartTime().isBefore( event.toLocalTime() ) && 
					ts.getEndTime().isAfter( event.toLocalTime() )
				)
			.toList()
			.get(0);
		if( slot != null ) {
			return slot.getStartTime()
					.atDate( event.toLocalDate() )
					.atZone( ZoneId.systemDefault() )
					.toInstant();
		} else {
			return getScheduledStart( slots, event.plusDays(1) );
		}
	}
	
}
