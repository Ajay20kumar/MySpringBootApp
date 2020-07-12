package com.centerpoint.servicehelpdesk.dto;

import com.centerpoint.servicehelpdesk.entity.UserLocation;
import com.centerpoint.servicehelpdesk.entity.UserRoles;

import lombok.Data;

public @Data class UserDTO {
	
	private Long userId; 
	
	private String userName;
	
	private String usersurName;

	private String password;
	
	private String userShortName;
	
	private String userEmail;
	
	private String  userCode;
	
	private String userPhone;
	
	private String userMobile;
	
	private String mobileSecondary;
	
	private DepartmentDTO departmentEntity;
	
	private UserRoles userRoles;
	
    private OrganizationDTO organization;
	
	private BranchDTO branches;
	
	private UserLocation userLocation;
	
	private String resetToken;

}
