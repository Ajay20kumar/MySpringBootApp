package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table ( name = "attachments")
@EqualsAndHashCode( callSuper = false)
public @Data class Attachments extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE ,generator = "ATTACHMENTS_SEQ")
	@SequenceGenerator( name = "ATTACHMENTS_SEQ" , sequenceName = "SEQUENCE_ATTACHMENTS")
	private Long attachmentId;
	
	private String fileName;
	
	@ManyToOne
	@JoinColumn(name = "ticketId",referencedColumnName = "ticketId")
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "responseId",referencedColumnName = "responseId")
	private TicketResponses ticketResponses;

}
