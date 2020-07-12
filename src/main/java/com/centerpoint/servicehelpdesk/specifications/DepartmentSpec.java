package com.centerpoint.servicehelpdesk.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.centerpoint.servicehelpdesk.entity.ServiceCategories;

public class DepartmentSpec {
	
	public static Specification<ServiceCategories> getByDepartmentId(Long departmentId) {
		return (r,cq,cb) -> cb.or(cb.equal(r.get("departmentId"), departmentId));
		
	}

}
