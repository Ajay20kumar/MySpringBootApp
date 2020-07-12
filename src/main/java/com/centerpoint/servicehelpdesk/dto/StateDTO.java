package com.centerpoint.servicehelpdesk.dto;

import java.util.List;

import lombok.Data;

public @Data class StateDTO {
	
	private Long stateId;
	private String stateName;
	private List<CityDTO> city;

}
