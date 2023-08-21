package com.gts.notifier.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gts.notifier.data.UserTimeSlot;
import com.gts.notifier.model.Event;
import com.gts.notifier.model.User;
import com.gts.notifier.service.NotificationService;
import com.gts.notifier.service.UserService;
import com.gts.notifier.task.NotificationTask;

import lombok.AllArgsConstructor;

/**
 * Implementation of {@link NotificationService}
 * @author gorbachevov
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationServiceImpl implements NotificationService {

	private UserService userService;
	
	private ThreadPoolTaskExecutor taskExecutor;
	
	private ThreadPoolTaskScheduler taskScheduler;
		
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendNotify(Event event) {
		List<User> users = userService.findAll();
		users.stream()
			 .map( u -> new NotificationTask(u, event) )
			 .forEach( nt -> notifyTask(nt) );
	}

	/**
	 * Executes or schedules a task
	 * @param task
	 */
	private void notifyTask(NotificationTask task) {
		if( task.getNotifiedUser().getTimeSlots() == null ) {
			taskExecutor.execute( task );
		} else {
			List<UserTimeSlot> weekDayIntervals = new ArrayList<UserTimeSlot>( task.getNotifiedUser().getTimeSlots() );
			LocalDateTime event = task.getNotifiedEvent().getEventDateTime();
			if( weekDayIntervals
					.stream()
					.sorted()
					.filter(ts -> 
							ts.getWeekDay().equals( event.getDayOfWeek() ) &&
							ts.getStartTime().isBefore( event.toLocalTime() ) && 
							ts.getEndTime().isAfter( event.toLocalTime() )
						)
					.toList()
					.size() > 0 ) {
				taskExecutor.execute( task );
			} else {
				taskScheduler.schedule( task, getScheduledStart( weekDayIntervals, event ) );
			}
		}
	}
	
	/**
	 * Takes <code>List<code> of timeslots for specific user and datetime of specific event
	 * @param slots
	 * @param event
	 * @return instant at start of next availability interval 
	 */
	private Instant getScheduledStart( List<UserTimeSlot> slots, LocalDateTime event ) {
		LocalDateTime opt = slots.stream()
				.sorted()
				.map( ts -> ts.getStartTime()
							  .atDate( LocalDate.now().with( TemporalAdjusters.nextOrSame(ts.getWeekDay())) ) 
					)
				.filter( dt -> dt.isAfter(event) )
				.findFirst().get();
		return opt.atZone( ZoneId.systemDefault() ).toInstant();
	}
	
}
