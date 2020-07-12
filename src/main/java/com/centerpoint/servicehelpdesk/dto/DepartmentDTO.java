package com.centerpoint.servicehelpdesk.dto;

import java.util.List;

import lombok.Data;

public @Data class DepartmentDTO {
	
	  private Long departmentId;
	  private String departmentName;
	  private String departmentShortName;
	 // private List<ServiceCategoryDTO> serviceCategories;

}
