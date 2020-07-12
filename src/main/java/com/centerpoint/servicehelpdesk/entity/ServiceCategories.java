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
@Table ( name = "servicecategories")
@EqualsAndHashCode (callSuper = false)
public @Data class ServiceCategories extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE ,generator = "SERVICECATEGORIES_SEQ")
	@SequenceGenerator( name = "SERVICECATEGORIES_SEQ" , sequenceName = "SEQUENCE_SERVICECATEGORIES")
	private Long serviceCategorieId;
	
	@NotEmpty( message = "Categorie Name must not be empty")
	private String serviceCategorieName;
	
	@ManyToOne
	@JoinColumn(name = "departmentId", nullable = false, referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity;
	
	@ManyToOne
	@JoinColumn(name = "activeId",nullable = false)
	private Active active;

}
