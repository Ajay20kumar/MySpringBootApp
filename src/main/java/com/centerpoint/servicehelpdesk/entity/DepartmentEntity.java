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
@Table(name = "department")
@EqualsAndHashCode (callSuper = false)
public @Data class DepartmentEntity extends CommonProperties implements Serializable {
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_SEQ")
  @SequenceGenerator( name = "DEPARTMENT_SEQ", sequenceName = "SEQUENCE_DEPARTMENT")
  private Long departmentId;
  
  @NotEmpty(message = "Department Name must not be empty")
  private String departmentName;
  
  @NotEmpty(message = "Department Short Name must not be empty")
  private String departmentShortName;
  
  @Email
  @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
  @NotEmpty(message = "Department Email must not be empty")
  private String departmentEmail;
  
  @NotEmpty(message = "Department Code must not be empty")
  @Size(min = 0,max = 5)
  @Pattern(regexp = "(^$|[0-9]{5})")
  private String departmentCode;
	
  @NotEmpty(message = "Department Phone must not be empty")
  @Pattern(regexp="\\d{3}-\\d{7}")
  @Size(min = 0,max = 11)
  private String departmentphone;
	
  @NotEmpty(message = "Department Mobile must not be empty")
  @Size(min = 0,max = 10)
  @Pattern(regexp="(^$|[0-9]{10})")
  private String departmentMobile;
  
  @ManyToOne
  @JoinColumn(name = "branchId", nullable = false, referencedColumnName = "branchId")
  private BranchesEntity branchesEntity;
  
  @OneToMany(cascade = CascadeType.ALL,mappedBy = "departmentEntity")
  private List<ServiceCategories> serviceCategories;
 
  @ManyToOne
  @JoinColumn(name = "activeId",nullable = false)
  private Active active;

}
