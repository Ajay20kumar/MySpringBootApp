package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table ( name = "ticketresponses")
@EqualsAndHashCode( callSuper = false)
public @Data class TicketResponses extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE ,generator = "TICKETRESPONSES_SEQ")
	@SequenceGenerator( name = "TICKETRESPONSES_SEQ" , sequenceName = "responseId_seq" , allocationSize = 1)
	private Long responseId;
	
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm")
	private Date responseDate;
	
	private String rmessage;
	
	private String treason;
	
	private String tmessage;
	
	private Integer rating;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ticketId",nullable = false, referencedColumnName = "ticketId")
	private Ticket ticket;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "rtypeId", referencedColumnName = "rtypeId")
	private ResponseType responsetype;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn( name = "userId",nullable = false ,referencedColumnName = "userId")
	private User user;
	 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn( name = "statusId",nullable = false ,referencedColumnName = "statusId")
	private Status status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "activeId")
	private Active active;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "po", referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity1;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "co", referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity2;
	
	private String publish;
	
	private Long parkedId;
	
   

}
