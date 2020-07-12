package com.centerpoint.servicehelpdesk.dto;

import java.io.Serializable;
import java.util.Date;

import com.centerpoint.servicehelpdesk.entity.Stages;
import com.centerpoint.servicehelpdesk.entity.Status;

import lombok.Data;

public @Data class TicketDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long ticketId;
	private String subject;
	private String message;
	private Date ticketDate;
    private String notifications;
    private Integer overDueDays;
    private DepartmentDTO departmentEntity;
    private ServiceCategoryDTO serviceCategories;
    private Stages stages;
    private Status status;
    private String pic;
    private TicketUser user1;
    private String tranforTicket;

}
