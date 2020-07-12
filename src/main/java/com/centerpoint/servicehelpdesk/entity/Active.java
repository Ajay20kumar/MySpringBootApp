package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table ( name = "active")
@EqualsAndHashCode (callSuper = false)
public @Data class Active extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "ACTIVE_SEQ")
	@SequenceGenerator( name = "ACTIVE_SEQ" , sequenceName = "SEQUENCE_ACTIVE")
	private Long activeId; 
	
	//@NotEmpty( message = "Active Name must not be empty")
	private String activeName;

}
