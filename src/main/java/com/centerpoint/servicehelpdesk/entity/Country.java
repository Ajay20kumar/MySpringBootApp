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
@Table(name = "country")
@EqualsAndHashCode (callSuper = false)
public @Data class Country extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "COUNTRY_SEQ") 
	@SequenceGenerator(name = "COUNTRY_SEQ" , sequenceName = "SEQUENCE_COUNTRY")
	private Long countryId;
	
	@NotEmpty( message = "Country Name must not be empty")
	private String countryName;
	
	@OneToMany( cascade = CascadeType.ALL , mappedBy = "country")
	private List<State> state;
	
	@ManyToOne
	@JoinColumn( name = "activeId" , nullable = false)
	private Active active;

}
