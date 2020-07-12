package com.centerpoint.servicehelpdesk.specifications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.centerpoint.servicehelpdesk.entity.Ticket;

public class TicketSpec implements Specification<Ticket> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Specification<Ticket> getDepartment(Long departmentId) {
		return (r,cq,cb) -> cb.and(cb.equal(r.get("departmentEntity1").get("departmentId"), departmentId));	
	}
	

	public static Specification<Ticket> getDepartment2(Long deptId) {
		return (r,cq,cb) -> cb.or(cb.equal(r.get("departmentEntity2").get("departmentId"), deptId));	
	}

	public static Specification<Ticket> searchName(String searchText) {
		return (r, cq, cb) -> {
		if (searchText == null || searchText.isEmpty())
			return cb.conjunction();
	 return cb.or(cb.or(cb.like(cb.lower(r.get("subject")), "%"+searchText.toLowerCase()+"%"))
			 ,cb.like(cb.lower(r.get("departmentEntity").get("departmentShortName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("status").get("statusName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("stages").get("stagesName")), "%"+searchText.toLowerCase()+"%")
			 ,cb.like(cb.lower(r.get("ticketDate").as(String.class)), "%"+searchText.toLowerCase()+"%"));
	};
	}

	public static Specification<Ticket> getuser(Long userId) {
		return (r,cq,cb) -> cb.and(cb.equal(r.get("user").get("userId"), userId));
	}

	public static Specification<Ticket> getstatus(Long statusId) {
		return (r, cq, cb) -> {
			if (statusId == null)
		     return cb.conjunction();
		return cb.or(cb.equal(r.get("status").get("statusId"),statusId));
		};
	}
	
	private final Ticket example;

	  public TicketSpec(Ticket example) {
	    this.example = example;
	  }
	
	@Override
	public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		 List<Predicate> predicates = new ArrayList<>();
		 if (example.getSdate() != null) {
		      predicates.add(cb.greaterThanOrEqualTo(root.get("ticketDate").as(String.class), example.getSdate()));
		    }
		 if (example.getTdate() != null) {
		      predicates.add(cb.lessThanOrEqualTo(root.get("ticketDate").as(String.class), example.getTdate()));
		    }
		 if (example.getDepartmentEntity().getDepartmentId() != null) {
		      predicates.add(cb.equal(root.get("departmentEntity").get("departmentId"),example.getDepartmentEntity().getDepartmentId()));
		    }
		if (example.getServiceCategories().getServiceCategorieId() != null) {
		      predicates.add(cb.equal(root.get("serviceCategories").get("serviceCategorieId"),example.getServiceCategories().getServiceCategorieId()));
		    }
		if (example.getStages().getStagesId() != null) {
		      predicates.add(cb.equal(root.get("stages").get("stagesId"),example.getStages().getStagesId()));
		    }
		if (example.getStatus().getStatusId() != null) {
		      predicates.add(cb.equal(root.get("status").get("statusId"),example.getStatus().getStatusId()));
		    }
		if (example.getUserDepartment().getDepartmentId() != null) {
		      predicates.add(cb.equal(root.get("userDepartment").get("departmentId"),example.getUserDepartment().getDepartmentId()));
		    }
		if (example.getAgentId() != 0) {
		      predicates.add(cb.equal(root.get("user1").get("userId"),example.getAgentId()));
		    }
		 return andTogether(predicates, cb);
	}
	
	 private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		    return cb.and(predicates.toArray(new Predicate[0]));
		  }


	 public static Specification<Ticket> getbetweendates(Date sdate, Date tdate) {
			return (r, cq, cb) -> {
				if (sdate == null && tdate == null)
			     return cb.conjunction();
			return cb.or(cb.between(r.get("ticketDate"), sdate, tdate));
			};
		}
}
