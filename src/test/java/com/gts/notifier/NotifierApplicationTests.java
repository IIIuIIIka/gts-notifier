package com.gts.notifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import com.gts.notifier.data.UserName;
import com.gts.notifier.data.UserTimeSlot;
import com.gts.notifier.model.Event;
import com.gts.notifier.model.User;
import com.gts.notifier.repository.EventRepository;
import com.gts.notifier.service.UserService;

import lombok.extern.slf4j.Slf4j;

@ComponentScan(basePackages = "com.gts.notifier")
@DataJpaTest
@Slf4j
class NotifierApplicationTests {

	@Autowired
	private EventRepository er;
	
	@Autowired
	private UserService us;
	
	@Test
	void userStoringTest() {
		List<UserTimeSlot> periods = new ArrayList<UserTimeSlot>();
		Integer[] days = {1,1,2,3,4,5,7};
		
		Arrays.asList(days)
			  .stream()
			  .map( i -> DayOfWeek.of(i) )
			  .forEach( d -> periods.add( new UserTimeSlot(d, LocalTime.NOON, LocalTime.MIDNIGHT) ) );
		
		UserName name = new UserName();
		name.setFirstName("Ivan");
		name.setLastName("Ivanov");
		User user = User.builder()
						.name(name)
						.timeSlots(periods)
						.build();
		user = us.create(user);
		
		assertEquals( user.getTimeSlots().get(3).getStartSlot(), LocalTime.NOON );
	}
	
	@Test
	void eventListenerTest() {
		log.info("Starting persistence test");
		Event e = new Event("My first event stored");
		e = er.save(e);
		log.info("Ending persistence test");
	}

}
