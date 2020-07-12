package com.centerpoint.servicehelpdesk.dto;

import lombok.Data;

public @Data class TicketUser {
	
private Long userId; 
private String userName;
private String usersurName;
private String userShortName;
private String  userCode;
}
