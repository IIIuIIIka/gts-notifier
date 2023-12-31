package com.gts.notifier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gts.notifier.data.UserTimeSlot;

import lombok.Data;

/**
 * @author gorbachevov
 * DTO for {@link UserTimeSlot} operating through REST API
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSlotDTO {

	@JsonProperty
	private String weekDay;
	@JsonProperty
	private String startTime;
	@JsonProperty
	private String endTime;

}
