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
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "status")
@EqualsAndHashCode (callSuper = false)
public @Data class Status extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "STATUS_SEQ")
	@SequenceGenerator(name = "STATUS_SEQ" , sequenceName = "SEQUENCE_STATUS")
	private Long statusId;
	
	@NotEmpty(message = "Status Name must not be empty")
	private String statusName;
	
	@ManyToOne
	@JoinColumn(name = "activeId",nullable = false)
	private Active active;

}
