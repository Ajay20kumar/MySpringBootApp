package com.centerpoint.servicehelpdesk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "parkedreason")
public @Data class ParkedReason {

	@Id
	@GeneratedValue
	private Long parkedId;

	private String preason;

	@ManyToOne
	@JoinColumn(name = "departmentId", nullable = false, referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity;

	@ManyToOne
	@JoinColumn(name = "activeId")
	private Active active;

}
