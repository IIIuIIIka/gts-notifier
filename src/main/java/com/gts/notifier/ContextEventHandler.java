package com.gts.notifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * Gracefully shutdown all schedulled tasks
 * @author gorbachevov
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContextEventHandler implements ApplicationListener<ContextClosedEvent>{
	
	private ThreadPoolTaskExecutor executor;
	private ThreadPoolTaskScheduler scheduler;
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		scheduler.shutdown();
		executor.shutdown();
	}
}
