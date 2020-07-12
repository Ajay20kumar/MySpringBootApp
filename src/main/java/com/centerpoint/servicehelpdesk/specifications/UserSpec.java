package com.centerpoint.servicehelpdesk.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.centerpoint.servicehelpdesk.entity.User;

public class UserSpec {
	
	public static Specification<User> searchName(String searchText) {
		return (r, cq, cb) -> {
		if (searchText == null || searchText.isEmpty())
			return cb.conjunction();
		
	 return cb.or(cb.or(cb.like(cb.lower(r.get("departmentEntity").get("departmentShortName")), "%"+searchText.toLowerCase()+"%"))
			 ,cb.like(cb.lower(r.get("userCode")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("userName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("usersurName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("userName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("userMobile")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("userRoles").get("roleName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("userLocation").get("locationName")), "%"+searchText.toLowerCase()+"%"));
	};
	}

	public static Specification<User> getactive(long a) {
		return (r,cq,cb) -> cb.and(cb.equal(r.get("active").get("activeId"), a));
	}
}
