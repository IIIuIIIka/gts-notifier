package com.gts.notifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gts.notifier.model.Event;
import com.gts.notifier.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.gts.notifier.service")
@DataJpaTest
@Slf4j
class NotifierApplicationTests {

	@Autowired
	private EventRepository er;
	
	@Test
	void accessingRepository() {
		log.info("Starting persistence test");
		Event e = new Event("My first event stored");
		e = er.save(e);
		log.info("Ending persistence test");
		log.info( e.toString() );
	}

}
