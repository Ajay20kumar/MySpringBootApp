package com.centerpoint.servicehelpdesk.dto;

import java.util.List;

import lombok.Data;

public @Data class CountryDTO {
	
	private Long countryId;
	private String countryName;
	private List<StateDTO> state;
	
}
