package com.centerpoint.servicehelpdesk.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
public @Data class CommonProperties {
	
	@Column
	//@NotEmpty
	private String CreatedBy;
	
	@Column
	//@NotEmpty
	private String CreatedDate;
	
	@Column
	//@NotEmpty
	private String CreatedIp;
	
	@Column
	//@NotEmpty
	private String UpdatedBy;
	
	@Column
	//@NotEmpty
	private String UpdatedDate;
	
	@Column
	//@NotEmpty
	private String UpdatedIp;
	
	
}
