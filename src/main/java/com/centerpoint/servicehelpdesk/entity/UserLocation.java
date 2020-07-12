package com.centerpoint.servicehelpdesk.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Entity
@Table(name = "userlocation")
public @Data class UserLocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "LOCATION_SEQ")
	@SequenceGenerator( name = "LOCATION_SEQ" , sequenceName = "SEQUENCE_USERLOCATION")
	private Long locationId;
	
	@NotEmpty(message = "User Location must not be empty")
	private String locationName;

}
