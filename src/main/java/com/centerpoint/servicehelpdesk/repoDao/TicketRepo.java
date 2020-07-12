package com.centerpoint.servicehelpdesk.repoDao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.User;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

	List<Ticket> findAllByUserAndStatus(User u, Status status);

	List<Ticket> findAllByStatus(Status status);

	Page<Ticket> findAll(Specification<Ticket> specification, Pageable pageable);

	List<Ticket> findAll(Specification<Ticket> specification, Sort ascending);

	Ticket findByTicketId(Long ticketId);

     long count(Specification<Ticket> getstatus);
	
	@Query("SELECT count(t.departmentEntity.departmentId),d.departmentName FROM Ticket as t,DepartmentEntity as d where (t.departmentEntity.departmentId = d.departmentId) and t.status.statusId = 1 and t.ticketDate BETWEEN ?1 and ?2  group by t.departmentEntity.departmentId,d.departmentName")
	List<Object> getDepartmentOpenCount(Date sdate,Date tdate);
	
	@Query("SELECT count(t.departmentEntity.departmentId),d.departmentName FROM Ticket as t,DepartmentEntity as d where (t.departmentEntity.departmentId = d.departmentId) and t.status.statusId = 2 and t.ticketDate BETWEEN ?1 and ?2 group by t.departmentEntity.departmentId,d.departmentName")
	List<Object> getDepartmentCloseCount(Date sdate,Date tdate);
	
	@Query("SELECT count(t.departmentEntity.departmentId),d.departmentName FROM Ticket as t,DepartmentEntity as d where (t.departmentEntity.departmentId = d.departmentId) and t.ticketDate BETWEEN ?1 and ?2  group by t.departmentEntity.departmentId,d.departmentName")
	List<Object> getDepartmentAllCount(Date sdate, Date tdate);
	
	@Query("SELECT count(t.serviceCategories.serviceCategorieId),d.serviceCategorieName FROM Ticket as t,ServiceCategories as d where (t.serviceCategories.serviceCategorieId = d.serviceCategorieId) and t.ticketDate BETWEEN ?1 and ?2  group by t.serviceCategories.serviceCategorieId,d.serviceCategorieName")
	List<Object> getAllServiceCategoryCount(Date sdate, Date tdate);
	
	@Query("SELECT count(t.user1.userId),u.userName,u.usersurName FROM Ticket as t, User as u, UserRoles as ur where (t.user1.userId = u.userId) and (u.userRoles.roleId = ur.roleId) and ur.roleId = 3 and t.status.statusId = 2 and t.ticketDate BETWEEN ?1 and ?2  group by t.user1.userId,u.userName,u.usersurName")
	List<Object> getAgentWiseAllTickets(Date sdate, Date tdate);

	
}
