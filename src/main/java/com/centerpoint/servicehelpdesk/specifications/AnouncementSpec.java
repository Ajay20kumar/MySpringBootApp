package com.centerpoint.servicehelpdesk.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.centerpoint.servicehelpdesk.entity.AnouncementEntity;

public class AnouncementSpec {

	public static Specification<AnouncementEntity> getactive(long l) {
		return (r,cq,cb) -> cb.or(cb.equal(r.get("active").get("activeId"),l));
	}
	public static Specification<AnouncementEntity> searchName(String searchText) {
		return (r, cq, cb) -> {
		if (searchText == null || searchText.isEmpty())
			return cb.conjunction();
		
	 return cb.or(cb.or(cb.like(cb.lower(r.get("message")), "%"+searchText.toLowerCase()+"%"))
			 ,cb.like(cb.lower(r.get("userLocation").get("locationName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("startDate").as(String.class)), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("endDate").as(String.class)), "%"+searchText.toLowerCase()+"%"));
	};
	}

}
