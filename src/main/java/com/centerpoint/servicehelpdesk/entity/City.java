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
@Table(name = "city")
@EqualsAndHashCode (callSuper = false)
public @Data class City extends CommonProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "CITY_SEQ") 
	@SequenceGenerator(name = "CITY_SEQ" , sequenceName = "SEQUENCE_CITY")
	private Long cityId;
	
	@NotEmpty( message = "City Name must not be empty")
	private String cityName;
	
	@ManyToOne
	@JoinColumn( name = "stateId" ,  nullable = false ,referencedColumnName = "stateId")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "activeId", nullable = false)
	private Active active;

}
