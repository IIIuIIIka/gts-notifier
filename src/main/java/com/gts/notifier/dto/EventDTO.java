package com.gts.notifier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gts.notifier.model.Event;

import lombok.Data;

/**
 * DTO for {@link Event} entity operating through REST API
 * @author gorbachevov
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {

	@JsonProperty
	private String message;
	@JsonProperty
	private String eventDateTime;
	
}
