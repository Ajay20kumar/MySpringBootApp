package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ticket")
@EqualsAndHashCode( callSuper = false)
public @Data class Ticket extends CommonProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "TICKET_SEQ")
	@SequenceGenerator(name = "TICKET_SEQ" , sequenceName = "ticketId_seq" ,allocationSize = 1)
	private Long ticketId;
	
	private String subject;
	
	private String message;
	
    @Temporal(TemporalType.TIMESTAMP)
	private Date ticketDate;
    
    @ManyToOne
    @JoinColumn( name = "userDepartment",referencedColumnName = "departmentId")
    private DepartmentEntity userDepartment;
    
    private String notifications;
    
    private Integer overDueDays;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "statusId",nullable = false ,referencedColumnName = "statusId")
    private Status status;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "StagesId",nullable = false ,referencedColumnName = "StagesId")
    private Stages stages;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "userId" ,nullable = false , referencedColumnName = "userId")
    private User user;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "assignedTo" , referencedColumnName = "userId")
    private User user1;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "departmentId",nullable = false ,referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "po",referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity1;
    
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "co" ,referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity2;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn( name = "serviceCategorieId",nullable = false ,referencedColumnName = "serviceCategorieId")
    private ServiceCategories serviceCategories;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ticket")
	private List<TicketResponses> ticketResponses;
    
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "activeId")
	private Active active;
    
    @Transient
    private String sdate;
    
    @Transient
    private String tdate;
    
    private String pic;
    
    @Lob
    private byte[] data;
    
    @Transient
    private Long agentId;

}
