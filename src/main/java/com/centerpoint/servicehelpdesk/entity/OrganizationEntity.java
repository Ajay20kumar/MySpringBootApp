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
@Table(name = "organization")
@EqualsAndHashCode (callSuper = false)
public @Data class OrganizationEntity extends CommonProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_SEQ")
    @SequenceGenerator(name = "ORGANIZATION_SEQ", sequenceName = "SEQUENCE_ORGANIZATION")
	private Long organizationId;

	@NotEmpty(message = "Organiztion Name must not be empty")
	private String organiztionName;
	
	@NotEmpty(message = "Organiztion Address must not be empty")
	private String organiztionAddress;
	
	@Email
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@NotEmpty(message = "Organiztion Email must not be empty")
	private String organiztionEmail;
	
	@NotEmpty(message = "Organiztion Phone must not be empty")
	@Pattern(regexp="\\d{3}-\\d{7}")
	@Size(min = 0, max = 11)
	private String organiztionphone;
	
	@NotEmpty(message = "Organiztion Mobile must not be empty")
	@Size(min = 0, max = 10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String organiztionMobile;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "organizationEntity")
	private List<BranchesEntity> branchesEntities;
	
	@ManyToOne
	@JoinColumn(name = "activeId",nullable = false)
	private Active active;
	
	@ManyToOne
	@JoinColumn(name = "cityId",nullable = false)
	private City city;

}
