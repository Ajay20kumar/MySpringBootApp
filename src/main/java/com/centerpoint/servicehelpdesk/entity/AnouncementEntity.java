package com.centerpoint.servicehelpdesk.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table (name="anouncements")
public @Data class AnouncementEntity {
	
	@Id
	@GeneratedValue
	private Long anouncementId;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	private String message;
	
	@ManyToOne
	@JoinColumn(name="locationId",nullable = false ,referencedColumnName = "locationId")
	private UserLocation userLocation;
	
	@ManyToOne
	@JoinColumn(name="serviceCategorieId" ,referencedColumnName = "serviceCategorieId")
	private ServiceCategories serviceCategories;
	
	@ManyToOne
	@JoinColumn(name="departmentId",referencedColumnName = "departmentId")
	private DepartmentEntity departmentEntity;
	
	@ManyToOne
	@JoinColumn(name="userId",nullable = false ,referencedColumnName = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "activeId")
	private Active active;

}
