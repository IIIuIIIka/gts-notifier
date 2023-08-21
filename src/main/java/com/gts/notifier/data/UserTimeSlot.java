package com.gts.notifier.data;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSlot implements Comparable<UserTimeSlot> {
	
	@Column(name="week_day")
	private DayOfWeek weekDay;
	
	@Column(name="start_time")
	private LocalTime startTime;
	
	@Column(name="end_time")
	private LocalTime endTime;

	@Override
	public int compareTo(UserTimeSlot o) {
		if( this.weekDay.compareTo( o.getWeekDay() ) != 0 ) {
			return this.weekDay.compareTo( o.getWeekDay() );
		} else {
			return startTime.compareTo( o.getStartTime() );
		}
	}
}