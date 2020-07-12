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
@Table ( name = "responsetype")
@EqualsAndHashCode( callSuper = false)
public @Data class ResponseType extends CommonProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE ,generator = "RESPONSETYPE_SEQ")
	@SequenceGenerator( name = "RESPONSETYPE_SEQ" , sequenceName = "SEQUENCE_RESPONSETYPE")
	private Long rtypeId;
	
	private String rName;
	
	private String colorCode;

}
