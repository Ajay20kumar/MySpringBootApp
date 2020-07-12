package com.centerpoint.servicehelpdesk.dto;

import java.util.Date;

import lombok.Data;

public @Data class TicketResponseDTO {
	
	private Long responseId;
	private Date responseDate;
	private String rmessage;
	private Integer rating;
	private TicketUser user;

}      
