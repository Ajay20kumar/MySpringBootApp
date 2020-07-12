package com.centerpoint.servicehelpdesk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name = "userroles")
public @Data class UserRoles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "USERROLES_SEQ")
	@SequenceGenerator(name = "USERROLES_SEQ" , sequenceName = "SEQUENCE_USERROLES")
	private Long roleId;
	
	@NotEmpty(message = "Role Name must not be empty")
	private String roleName;

}
