package com.centerpoint.servicehelpdesk.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@EqualsAndHashCode (callSuper = false)
public @Data class User extends CommonProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "USER_SEQ")
	@SequenceGenerator( name = "USER_SEQ" , sequenceName = "SEQUENCE_USER")
	private Long userId; 
	
	@NotEmpty(message = "User Name must not be empty")
	private String usersurName;
	
	@NotEmpty(message = "User SurName must not be empty")
	private String userName;
	
	//@NotEmpty(message = "User Password must not be empty")
	private String password;
	
	@NotEmpty(message = "User Short Name must not be empty")
	private String userShortName;
	
	@Column(unique = true)
	private String userCode;
	
	@Email
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@NotEmpty(message = "User Email must not be empty")
	private String userEmail;
	
	@Pattern(regexp = "^[+0-9-\\(\\)\\s]*{10,25}$")
	@NotEmpty(message = "User Phone must not be empty")
	@Size(min = 10, max = 12)
	private String userPhone; 
	
	@NotEmpty(message = "User Mobile must not be empty")
	@Size(min = 10, max = 12)
	@Pattern(regexp = "^[+0-9-\\(\\)\\s]*{10,25}$", message="Must be enter Phone number only")
	private String userMobile;
	
	/*@Size(min = 10, max = 12)
	@Pattern(regexp = "(^$|[0-9]{10})")*/
	private String mobileSecondary;
	
	@ManyToOne
	@JoinColumn(name = "departmentId", nullable = false, referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity;
	
	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false, referencedColumnName = "roleId")
	private UserRoles userRoles;
	
	@ManyToOne
	@JoinColumn(name = "organizationId", referencedColumnName = "organizationId")
	private OrganizationEntity organization;
	
	@ManyToOne
	@JoinColumn(name = "branchId" , referencedColumnName = "branchId")
	private BranchesEntity branches;
	
	@ManyToOne
	@JoinColumn(name = "locationId" , referencedColumnName = "locationId")
	private UserLocation userLocation;
	
	@ManyToOne
	@JoinColumn(name = "activeId")
	private Active active;
	
	private String resetToken;
	
	public User() {
		super();
	}

}
