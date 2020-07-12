package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table( name = "state")
@EqualsAndHashCode (callSuper = false)
public @Data class State extends CommonProperties implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "STATE_SEQ") 
		@SequenceGenerator(name = "STATE_SEQ" , sequenceName = "SEQUENCE_STATE")
		private Long stateId;
		
		@NotEmpty( message = "State Name must not be empty")
		private String stateName;
		
		@ManyToOne
		@JoinColumn( name = "countryId" , nullable = false ,referencedColumnName = "countryId")
		private Country country;
		
		@OneToMany( cascade = CascadeType.ALL , mappedBy = "state")
		private List<City> city;
		
		@ManyToOne
		@JoinColumn(name = "activeId",nullable = false)
		private Active active;
}
