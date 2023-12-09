package org.healthylifestyle.event.common.dto;

import jakarta.validation.constraints.NotBlank;

public class EventSaveRequest {
	private String place;
	@NotBlank
	private String description;

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
