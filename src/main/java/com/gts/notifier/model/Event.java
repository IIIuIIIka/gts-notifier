package com.gts.notifier.model;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.gts.notifier.listener.EventListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author gorbachevov
 */

@Data
@Entity
@NoArgsConstructor
@Table(name="events")
@RequiredArgsConstructor
@EntityListeners(EventListener.class)
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(name="message")
	private String message;
	
	@Column(name="event_date_time")
	private LocalDateTime eventDateTime;
}
