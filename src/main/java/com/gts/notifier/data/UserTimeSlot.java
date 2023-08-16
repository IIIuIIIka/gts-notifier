package com.gts.notifier.data;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSlot {
	
	@Nullable
	@Column(name="week_day")
	private DayOfWeek day;
	
	@Column(name="start_slot")
	private LocalTime startSlot;
	
	@Column(name="end_slot")
	private LocalTime endSlot;
}