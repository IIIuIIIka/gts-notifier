package com.gts.notifier.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
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
			 .map(u -> new NotificationTask(u, event))
			 .forEach(nt -> {
				 if( isAvailable(nt.getNotifiedUser(), event.getEventDateTime() ) ) {
					 doNotify(nt);
				 } else {
					 scheduleNotify(nt, Instant.EPOCH);
				 }
			 });
	}

	@Async
	private void doNotify(NotificationTask task) {
		taskExecutor.execute( task );
	}
	
	private void scheduleNotify(NotificationTask task, Instant start) {
		taskScheduler.schedule(task, start);
	}
	
	/**
	 * 
	 * @param user
	 * @param event
	 * @return
	 */
	private boolean isAvailable(User user, LocalDateTime event) {
		List<UserTimeSlot> intervals = 
				user.getTimeSlots()
					.stream()
					.filter(ts -> ts.getDay().equals( event.getDayOfWeek() ) &&
								  ts.getStartSlot().isBefore( event.toLocalTime() ) && 
								  ts.getEndSlot().isAfter( event.toLocalTime() )
							)
					.toList();
		if( intervals.size() > 0 )
			return true;
		else
			return false;
	}
	
	/*
	 * private Instant getScheduledStart(User user, LocalDateTime event) {
	 * List<UserTimeSlot> weekDayIntervals = user.getTimeSlots() .stream() .filter(
	 * ts -> ts.getDay().equals( event.getDayOfWeek() ) ) .toList(); if(
	 * weekDayIntervals.stream() .filter( i -> i.getStartSlot().isBefore(
	 * event.toLocalTime() ) && i.getEndSlot().isAfter( event.toLocalTime() ) )
	 * .toList() .size() > 0 ) { return Instant.now(); } else {
	 * 
	 * } }
	 */
}
