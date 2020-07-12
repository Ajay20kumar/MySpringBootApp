package com.centerpoint.servicehelpdesk.dto;

import java.util.Date;

import com.centerpoint.servicehelpdesk.entity.UserLocation;

import lombok.Data;

public @Data class AnouncementDTO {
	
   private Long anouncementId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String message;
	
	private UserLocation userLocation;
}
