package com.gts.notifier.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gts.notifier.model.User;

import lombok.Data;

/**
 * DTO for {@link User} entity operating through REST API
 * @author gorbachevov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	@JsonProperty
	private String firstName;
	@JsonProperty
	private String middleName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private List<TimeSlotDTO> timeSlots;
	
}
