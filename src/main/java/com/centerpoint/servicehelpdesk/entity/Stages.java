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
@Table( name = "stages")
@EqualsAndHashCode (callSuper = false)
public @Data class Stages extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "STAGES_SEQ")
	@SequenceGenerator(name = "STAGES_SEQ" , sequenceName = "SEQUENCE_STAGES")
	private Long stagesId;
	
	@NotEmpty(message = "Stages Name must not be empty")
	private String stagesName;
	
	
	private String stagecolor;
	
	
	@ManyToOne
	@JoinColumn(name = "activeId",nullable = false)
	private Active active;
	
	

}
