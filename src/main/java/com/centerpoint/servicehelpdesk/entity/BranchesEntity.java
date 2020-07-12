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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "branches")
@EqualsAndHashCode (callSuper = false)
public @Data class BranchesEntity extends CommonProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRANCHES_SEQ")
	@SequenceGenerator(name = "BRANCHES_SEQ", sequenceName = "SEQUENCE_BRANCHES")
	private Long branchId;
	
	@NotEmpty(message = "Branch Name must not be empty")
	private String branchName;
	
	@NotEmpty(message = "Branch Short Name must not be empty")
	private String branchShortName;
	
	@NotEmpty(message = "Branch Address must not be empty")
	private String branchAddress;
	
	@NotEmpty(message = "Branch Code must not be empty")
	@Size(min = 0,max = 5)
	@Pattern(regexp = "(^$|[0-9]{5})")
	private String branchCode;
	
	@Email
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@NotEmpty(message = "Branch Email must not be empty")
	private String branchEmail;
	
	@NotEmpty(message = "Branch Phone must not be empty")
	@Pattern(regexp = "\\d{3}-\\d{7}")
	@Size(min = 0,max = 11)
	private String branchphone;
	
	@NotEmpty(message = "Branch Mobile must not be empty")
	@Size(min = 0,max = 10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String branchMobile;
	
	@ManyToOne
	@JoinColumn(name = "organizationId",referencedColumnName = "organizationId",nullable = false)
	private OrganizationEntity organizationEntity;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branchesEntity")
	private List<DepartmentEntity> departmentEntities;
	
	@ManyToOne
	@JoinColumn(name = "activeId",nullable = false)
	private Active active;
	
	@ManyToOne
	@JoinColumn(name = "cityId",nullable = false)
	private City city;
	
}