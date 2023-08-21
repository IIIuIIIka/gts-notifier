package com.gts.notifier;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author gorbachevov
 */

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class NotifierApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NotifierApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		
		Provider<LocalTime> localTimeProvider = new AbstractProvider<LocalTime>() {
			@Override
			protected LocalTime get() {
				return LocalTime.now();
			}
		};
		
		Converter<String, LocalTime> toStringTime = new AbstractConverter<String, LocalTime>() {
			@Override
			protected LocalTime convert(String source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm[:ss][.SSS]");
				LocalTime localTime = LocalTime.parse(source, format);
				return localTime;
			}
		};
		
		mapper.createTypeMap(String.class, LocalTime.class);
		mapper.addConverter(toStringTime);
		mapper.getTypeMap(String.class, LocalTime.class).setProvider(localTimeProvider);
		
		return mapper;
	}
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
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
