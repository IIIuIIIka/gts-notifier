package com.gts.notifier;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class NotifierApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NotifierApplication.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("Notifier-executor-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
	   ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	   scheduler.setPoolSize(5);
	   scheduler.setThreadNamePrefix("Notifier-scheduler-");
	   return scheduler;
	}
}
