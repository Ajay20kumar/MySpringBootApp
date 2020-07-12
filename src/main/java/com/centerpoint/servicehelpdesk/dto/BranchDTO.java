package com.centerpoint.servicehelpdesk.dto;

import lombok.Data;

public @Data class BranchDTO {
	
	private Long branchId;
	private String branchName;
	private String branchShortName;
	private String branchAddress;
	private String branchCode;
	private String branchEmail;
	private String branchphone;
	private String branchMobile;
}
