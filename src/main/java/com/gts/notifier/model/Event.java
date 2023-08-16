package com.gts.notifier.model;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(name="message")
	private String message;
	
	@NonNull
	@Column(name="event_date_time")
	private LocalDateTime eventDateTime;
	
	@PrePersist
	public void createdAt() {
		eventDateTime = LocalDateTime.now();
	}
	
}
